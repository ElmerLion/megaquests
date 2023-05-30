package com.elmer.megaquests.commands;

import com.elmer.megaquests.ItemBuilder;
import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

    private static final Random random = new Random();
    private final MegaQuests megaQuests;
    private final QuestManager questManager;
    List<ItemStack> questItems = new ArrayList<>();
    List<Quests> questsData = new ArrayList<>();
    List<Integer> taskAmounts = new ArrayList<>();
    private Inventory questGUI;

    public QuestGUICommand(MegaQuests megaQuests, QuestManager questManager) {
        this.megaQuests = megaQuests;
        this.questManager = questManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("megaquests.use")) {
                megaQuests.getCooldownManager().checkCooldown(player);
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
        }

        return false;
    }

    public void createQuestGUI(Player player) {
        questGUI = Bukkit.createInventory(player, 27, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests");

        int startingSlot = questManager.getStartingSlot();
        int endingSlot = questManager.getEndingSlot();


        UUID playerUUID = player.getUniqueId();

        questItems.clear();
        questsData.clear();
        taskAmounts.clear();
        questManager.resetPlayerQuestProgress(playerUUID);


        for (Quests quests : Quests.values()) {
            if (questManager.getPlayerProgressMap() != null) {
                questManager.resetPlayerQuestProgress(playerUUID);
            }
            quests.setCompleted(false);
            quests.resetTaskAmount();
        }

        Quests[] availableQuests = Arrays.stream(Quests.values())
                .filter(Quests::isEnabled)
                .toArray(Quests[]::new);

        if (availableQuests.length >= 5) {
            for (int i = startingSlot; i <= endingSlot; i++) {

                int randomIndex = random.nextInt(availableQuests.length);
                Quests quest = availableQuests[randomIndex];

                int minTask = quest.getMinTask();
                int maxTask = quest.getMaxTask();

                int taskAmount = random.nextInt(maxTask - minTask + 1) + minTask;

                ItemStack questItem = new ItemBuilder(quest.getItemDisplay())
                        .withDisplayName(quest.getDisplay() + " " + ChatColor.GRAY + questManager.getProgress(playerUUID, quest) + "/" + taskAmount)
                        .withLore(ChatColor.GRAY + "Completion Reward $" + quest.getReward()).build();

                availableQuests = removeQuest(availableQuests, randomIndex);

                questGUI.setItem(i, questItem);

                quest.setTaskAmount(taskAmount);

                questItems.add(questItem);
                questsData.add(quest);
                taskAmounts.add(taskAmount);
            }
        } else {
            Bukkit.getLogger().log(Level.WARNING, "MegaQuests: Too many quests are disabled, Enable more in quests.yml!");
        }

        createQuestTimer();

        player.openInventory(questGUI);


    }

    public void openQuestGUI(Player player) {

        int slot = questManager.getStartingSlot();
        for (int i = 0; i < questItems.size(); i++) {
            String itemDisplayName = questsData.get(i).getDisplay() +
                    " " +
                    ChatColor.GRAY +
                    questManager.getProgress(player.getUniqueId(), questsData.get(i)) +
                    "/" +
                    taskAmounts.get(i);

            ItemStack questItem = new ItemBuilder(questsData.get(i).getItemDisplay())
                    .withDisplayName(itemDisplayName)
                    .withLore(ChatColor.GRAY + "Completion Reward $" + questsData.get(i).getReward()).build();

            questItems.set(i, questItem);
            questGUI.setItem(slot, questItems.get(i));
            slot++;
        }

        player.sendMessage("Opened Updated GUI");

        createQuestTimer();
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

    public void createQuestTimer() {
        boolean isTimeMoreOneHour = megaQuests.getCooldownManager().getTimeToWait() >= TimeUnit.HOURS.toMillis(1);

        String questRestTimerItemDisplayName = String.valueOf(ChatColor.GOLD) +
                (isTimeMoreOneHour
                        ? TimeUnit.MILLISECONDS.toHours(megaQuests.getCooldownManager().getTimeToWait())
                        : TimeUnit.MILLISECONDS.toMinutes(megaQuests.getCooldownManager().getTimeToWait())) +
                " " +
                (isTimeMoreOneHour ? "hours" : "minutes") +
                " " +
                "until reset.";

        ItemStack questRestTimerItem = new ItemBuilder(Material.CLOCK)
                .withDisplayName(questRestTimerItemDisplayName)
                .build();

        getQuestGUI().setItem(26, questRestTimerItem);
    }
}
