package com.elmer.megaquests.commands;

import com.elmer.megaquests.ItemBuilder;
import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.CooldownManager;
import com.elmer.megaquests.managers.QuestManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class QuestGUICommand implements CommandExecutor {

    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
    private final MegaQuests megaQuests;
    private final QuestManager questManager;
    private static final Random random = new Random();
    List<ItemStack> questItems = new ArrayList<>();
    private Map<UUID, Inventory> questInventories;
    private Map<UUID, List<Quests>> playerQuestsData;
    private Map<UUID, List<Integer>> playerTaskAmounts;
    List<Quests> questsData = new ArrayList<>();
    List<Integer> taskAmounts = new ArrayList<>();
    Set<UUID> playersWithInventory = new HashSet<>();

    public QuestGUICommand(MegaQuests megaQuests, QuestManager questManager){
        this.megaQuests = megaQuests;
        this.questManager = questManager;

        this.questInventories = new HashMap<>();
        this.playerQuestsData = new HashMap<>();
        this.playerTaskAmounts = new HashMap<>();
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player){
            if (player.hasPermission("megaquests.use")){
                if (questManager.isCooldownBased()){
                    megaQuests.getCooldownManager().checkCooldown(player);
                }
                if (!questManager.isCooldownBased()){
                    UUID playerId = player.getUniqueId();
                    if (megaQuests.getCooldownManager().isGlobalCooldownActive()){
                        if (playersWithInventory.contains(playerId)) {
                            openQuestGUI(player);
                        } else {
                            createQuestGUI(player);
                            playersWithInventory.add(playerId);
                        }
                    }
                } else {
                        Bukkit.getLogger().log(Level.WARNING, "MegaQuests: Global Cooldown is not active, try using /resetglobaltimer");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
        }


        return false;
    }
    public void createQuestGUI(Player player){
        playerTaskAmounts.remove(player.getUniqueId());
        playerQuestsData.remove(player.getUniqueId());

        Inventory questGUI = Bukkit.createInventory(player, 27, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests");

        int startingSlot = questManager.getStartingSlot();
        int endingSlot = questManager.getEndingSlot();

        UUID playerId = player.getUniqueId();

        questItems.clear();
        questsData.clear();
        taskAmounts.clear();
        questManager.resetPlayerQuestProgress(playerId);


        for (Quests quests : Quests.values()){
            if(questManager.getPlayerProgressMap() != null){
                questManager.resetPlayerQuestProgress(playerId);
            }
            questManager.completedQuests.clear();
        }

        Quests[] availableQuests = Arrays.stream(Quests.values())
                .filter(Quests::isEnabled)
                .toArray(Quests[]::new);

        List<Quests> selectedQuests = new ArrayList<>();

        if (availableQuests.length >= 5) {

            for (int i = startingSlot; i <= endingSlot; i++){

                if (availableQuests.length == 0) {
                    player.sendMessage(ChatColor.RED + "Error: Not enough quests are enabled!");
                    break;
                }

                int randomIndex = random.nextInt(availableQuests.length);
                Quests quest = availableQuests[randomIndex];

                int minTask = quest.getMinTask();
                int maxTask = quest.getMaxTask();

                int taskAmount = random.nextInt(maxTask - minTask + 1) + minTask;

                Map<Quests, Integer> taskAmountMap = questManager.getPlayerTaskAmountsMap().getOrDefault(playerId, new HashMap<>());
                taskAmountMap.put(quest, taskAmount);
                questManager.getPlayerTaskAmountsMap().put(playerId, taskAmountMap);

                ItemStack questItem = new ItemBuilder(quest.getItemDisplay())
                        .withDisplayName(quest.getDisplay() +  " " +  ChatColor.GRAY + questManager.getProgress(playerId, quest) + "/" + taskAmount)
                        .withLore(ChatColor.GRAY + "Completion Reward $" + quest.getReward()).build();

                selectedQuests.add(quest);
                availableQuests = removeQuest(availableQuests, randomIndex);

                questGUI.setItem(i, questItem);

                if (!playerQuestsData.containsKey(playerId) || !playerQuestsData.get(playerId).contains(quest)) {
                    questItems.add(questItem);
                    questsData.add(quest);
                    taskAmounts.add(taskAmount);

                    playerQuestsData.put(playerId, new ArrayList<>(questsData));
                    playerTaskAmounts.put(playerId, new ArrayList<>(taskAmounts));
                }
            }
        } else {
            Bukkit.getLogger().log(Level.WARNING, "MegaQuests: Too many quests are disabled, Enable more in quests.yml!");
        }

        questInventories.put(playerId, questGUI);
        createQuestTimer(player);

        player.openInventory(questInventories.get(playerId));

    }

    public void openQuestGUI(Player player) {
        Inventory questGUI = questInventories.get(player.getUniqueId());

        List<Quests> questsData = playerQuestsData.get(player.getUniqueId());
        List<Integer> taskAmounts = playerTaskAmounts.get(player.getUniqueId());


        for (int i = 0; i < questsData.size(); i++) {
            String itemDisplayName = questsData.get(i).getDisplay() + " " +
                    ChatColor.GRAY + questManager.getProgress(player.getUniqueId(), questsData.get(i)) + "/" + taskAmounts.get(i);

            ItemStack questItem = new ItemBuilder(questsData.get(i).getItemDisplay())
                    .withDisplayName(itemDisplayName)
                    .withLore(ChatColor.GRAY + "Completion Reward $" + questsData.get(i).getReward()).build();

            questGUI.setItem(questManager.getStartingSlot() + i, questItem);
        }

        questInventories.put(player.getUniqueId(), questGUI);
        createQuestTimer(player);

        player.openInventory(questInventories.get(player.getUniqueId()));
    }

    public Inventory getQuestGUI(Player player) {
        return questInventories.get(player.getUniqueId());
    }
    public Set<UUID> getPlayersWithInventory() {return playersWithInventory;}
    public Map<UUID, Inventory> getQuestGUIRaw(){
        return questInventories;
    }

    private Quests[] removeQuest(Quests[] quests, int index) {
        Quests[] newQuests = new Quests[quests.length - 1];
        System.arraycopy(quests, 0, newQuests, 0, index);
        System.arraycopy(quests, index + 1, newQuests, index, quests.length - index - 1);
        return newQuests;
    }

    public void createQuestTimer(Player player){
        CooldownManager cooldownManager = megaQuests.getCooldownManager();
        if (!questManager.isCooldownBased()){
            cooldownManager.checkGlobalCooldown();
        }
        ;

        if (cooldownManager.getTimeToWait() < TimeUnit.HOURS.toMillis(1)){
            ItemStack questResetTimer = new ItemBuilder(Material.CLOCK)
                    .withDisplayName(ChatColor.GOLD.toString()
                            + TimeUnit.MILLISECONDS.toMinutes(cooldownManager.getTimeToWait()) + " Minutes until reset.").build();


            getQuestGUI(player).setItem(26,questResetTimer);
        }

        if (cooldownManager.getTimeToWait() >= TimeUnit.HOURS.toMillis(1)){
            ItemStack questResetTimer = new ItemBuilder(Material.CLOCK)
                    .withDisplayName(ChatColor.GOLD.toString()
                            + TimeUnit.MILLISECONDS.toHours(cooldownManager.getTimeToWait()) + " Hours until reset.").build();

            getQuestGUI(player).setItem(26,questResetTimer);
        }
    }
}
