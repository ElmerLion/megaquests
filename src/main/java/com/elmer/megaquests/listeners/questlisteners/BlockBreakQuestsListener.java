package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;

public class BlockBreakQuestsListener implements Listener {
    MegaQuests megaQuests;
    public BlockBreakQuestsListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent e){
        Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();


        if (questGUI != null){
            Player player = e.getPlayer();
            Block brokenBlock = e.getBlock();
            QuestManager questManager = megaQuests.getQuestManager();
            for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot(); i++){
                if (brokenBlock.getType().equals(Material.DIAMOND_ORE)
                        && questGUI.getItem(i).getType().equals(Material.DIAMOND_ORE)
                        && !questManager.checkCompletedEnabled(player,Quests.MINEDIAMONDORE)){
                    questManager.checkCompletion(player, Quests.MINEDIAMONDORE,1);
                }
                if (brokenBlock.getType().equals(Material.IRON_ORE)
                        && questGUI.getItem(i).getType().equals(Material.IRON_ORE)
                        && !questManager.checkCompletedEnabled(player,Quests.MINEIRONORE)){
                    questManager.checkCompletion(player, Quests.MINEIRONORE, 1);
                }
                if(brokenBlock.getType().equals(Material.GOLD_ORE)
                        && questGUI.getItem(i).getType().equals(Material.GOLD_ORE)
                        && !questManager.checkCompletedEnabled(player,Quests.MINEGOLDORE)){
                    questManager.checkCompletion(player, Quests.MINEGOLDORE, 1);
                }
                if (brokenBlock.getType().equals(Material.COAL_ORE)
                        && questGUI.getItem(i).getType().equals(Material.COAL_ORE)
                        && !questManager.checkCompletedEnabled(player,Quests.MINECOALORE)){
                    questManager.checkCompletion(player, Quests.MINECOALORE, 1);
                }
                if(brokenBlock.getType().equals(Material.OAK_LOG)
                        && questGUI.getItem(i).getType().equals(Material.OAK_LOG)
                        && !questManager.checkCompletedEnabled(player,Quests.MINEOAKWOOD)){
                    questManager.checkCompletion(player, Quests.MINEOAKWOOD, 1);
                }
                if (brokenBlock.getType().equals(Material.BIRCH_LOG)
                        && questGUI.getItem(i).getType().equals(Material.BIRCH_LOG)
                        && !questManager.checkCompletedEnabled(player,Quests.MINEBIRCHWOOD)) {
                    questManager.checkCompletion(player, Quests.MINEBIRCHWOOD, 1);
                }
                if (brokenBlock.getType().equals(Material.DARK_OAK_LOG)
                        && questGUI.getItem(i).getType().equals(Material.DARK_OAK_LOG)
                        && !questManager.checkCompletedEnabled(player,Quests.MINEDARKOAKWOOD)) {
                    questManager.checkCompletion(player, Quests.MINEDARKOAKWOOD, 1);
                }
                if (brokenBlock.getType().equals(Material.ACACIA_LOG)
                        && questGUI.getItem(i).getType().equals(Material.ACACIA_LOG)
                        && !questManager.checkCompletedEnabled(player, Quests.MINEACACIAWOOD)) {
                    questManager.checkCompletion(player, Quests.MINEACACIAWOOD, 1);
                }
                if (brokenBlock.getType().equals(Material.JUNGLE_LOG)
                        && questGUI.getItem(i).getType().equals(Material.JUNGLE_LOG)
                        && !questManager.checkCompletedEnabled(player, Quests.MINEJUNGLEWOOD)) {
                    questManager.checkCompletion(player, Quests.MINEJUNGLEWOOD, 1);
                }
                if (brokenBlock.getType().equals(Material.SPRUCE_LOG)
                        && questGUI.getItem(i).getType().equals(Material.SPRUCE_LOG)
                        && !questManager.checkCompletedEnabled(player, Quests.MINESPRUCEWOOD)) {
                    questManager.checkCompletion(player, Quests.MINESPRUCEWOOD, 1);
                }
        }







        }


    }
}
