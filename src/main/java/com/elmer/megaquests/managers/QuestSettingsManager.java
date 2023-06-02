package com.elmer.megaquests.managers;

import com.elmer.megaquests.ItemBuilder;
import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class QuestSettingsManager {

    MegaQuests megaQuests;
    public QuestSettingsManager(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    public void createGUIWithCategory(Player player, String category){
        List<Quests> questsByCategory = new ArrayList<Quests>() {};

        if (!categoryExists(category)){
            player.sendMessage(ChatColor.RED + "That category doesn't exist!");
            return;
        }

        for (Quests quest : Quests.values()){
            if (quest.getCategory().equals(category)){
                questsByCategory.add(quest);
            }
        }
        Inventory questSettingInv = Bukkit.createInventory(player,  54, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests"
                + ChatColor.GRAY + " - Change Settings");

        for (int i = 0; i < questsByCategory.size(); i++){
            Quests quest = questsByCategory.get(i);
            ItemStack questItem = new ItemBuilder(quest.getItemDisplay()).withDisplayName(ChatColor.GOLD + quest.getDisplay())
                    .withLore(ChatColor.GOLD + "Enabled/Disabled: " + ChatColor.AQUA + quest.isEnabled()
                            , ChatColor.GOLD + "Reward: " + ChatColor.AQUA + quest.getReward()
                            , ChatColor.GOLD + "Min Tasks: " + ChatColor.AQUA + quest.getMinTask()
                            , ChatColor.GOLD + "Max Tasks: " + ChatColor.AQUA + quest.getMaxTask()).build();
            questSettingInv.addItem(questItem);
        }
        player.openInventory(questSettingInv);
    }
    public boolean categoryExists(String category) {
        for (Quests quest : Quests.values()){
            if (quest.getCategory().equals(category)){
                return true;
            }
        }
        return false;
    }


}
