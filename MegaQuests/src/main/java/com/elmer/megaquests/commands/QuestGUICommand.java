package com.elmer.megaquests.commands;

import com.elmer.megaquests.ItemBuilder;
import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.Quests;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuestGUICommand implements CommandExecutor {

    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
    private MegaQuests megaQuests;
    private static final Random random = new Random();
    private Inventory questGUI;
    private Quests quest;
    private int taskAmount;
    List<ItemStack> questItems = new ArrayList<>();
    List<Quests> questsData = new ArrayList<>();
    List<Integer> taskAmounts = new ArrayList<>();

    public QuestGUICommand(MegaQuests megaQuests){
        this.megaQuests = megaQuests;

    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("megaquests.use")){
                megaQuests.getCooldownManager().checkCooldown(player);
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }

        }

        return false;
    }
    public void createQuestGUI(Player player){
        questGUI = Bukkit.createInventory(player, 27, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests");


        UUID playerId = player.getUniqueId();

        ItemStack questResetTimer = new ItemBuilder(Material.CLOCK)
                .withDisplayName(ChatColor.RED.toString() + megaQuests.getCooldownManager().getTimeToWait() + " Hours until reset").build();
        questGUI.setItem(26,questResetTimer);

        questItems.clear();
        questsData.clear();
        taskAmounts.clear();
        megaQuests.getQuestManager().resetPlayerQuestProgress(playerId);


        for (Quests quests : Quests.values()){
            if(megaQuests.getQuestManager().getPlayerProgressMap() != null){
                megaQuests.getQuestManager().resetPlayerQuestProgress(playerId);
            }
            quests.setCompleted(false);
            quests.resetTaskAmount();
        }

        Quests[] availableQuests = Arrays.stream(Quests.values())
                .filter(Quests::isEnabled)
                .toArray(Quests[]::new);

        List<Quests> selectedQuests = new ArrayList<>();

        if (availableQuests.length > 0) {

            for (int i = megaQuests.getQuestManager().getQuestGUIAmount() + 6; i < megaQuests.getQuestManager().getQuestGUIAmount() + 11; i++){

                if (availableQuests.length == 0) {
                    break;
                }

                int randomIndex = random.nextInt(availableQuests.length);
                Quests quest = availableQuests[randomIndex];

                int minTask = quest.getMinTask();
                int maxTask = quest.getMaxTask();

                int taskAmount = random.nextInt(maxTask - minTask + 1) + minTask;

                ItemStack questItem = new ItemBuilder(quest.getItemDisplay())
                        .withDisplayName(quest.getDisplay() +  " " +  ChatColor.GRAY + megaQuests.getQuestManager().getProgress(playerId, quest) + "/" + taskAmount)
                        .build();

                selectedQuests.add(quest);
                availableQuests = removeQuest(availableQuests, randomIndex);

                questGUI.setItem(i, questItem);

                quest.setTaskAmount(taskAmount);

                questItems.add(questItem);
                questsData.add(quest);
                taskAmounts.add(taskAmount);
            }
        } else {
            System.out.println("MegaQuests: All quests are disabled, Enable them in quests.yml!");
        }


        player.openInventory(questGUI);



    }

    public void openQuestGUI(Player player) {
        int slot = megaQuests.getQuestManager().getQuestGUIAmount() + 6;
        for (int i = 0; i < questItems.size(); i++){
            questItems.set(i,new ItemBuilder(questsData.get(i).getItemDisplay())
                    .withDisplayName(questsData.get(i).getDisplay() +  " " + ChatColor.GRAY + megaQuests.getQuestManager().getProgress(player.getUniqueId(), questsData.get(i))  + "/" + taskAmounts.get(i))
                    .build());
            questGUI.setItem(slot, questItems.get(i));
            slot++;
        }

        player.sendMessage("Opened Updated GUI");

        player.openInventory(questGUI);
    }

    public Inventory getQuestGUI() {
        return questGUI;
    }
    private Quests[] removeQuest(Quests[] quests, int index) {
        Quests[] newQuests = new Quests[quests.length - 1];
        System.arraycopy(quests, 0, newQuests, 0, index);
        System.arraycopy(quests, index + 1, newQuests, index, quests.length - index - 1);
        return newQuests;
    }
}
