package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.Quests;
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
        Player player = e.getPlayer();
        Block brokenBlock = e.getBlock();
        Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();
        QuestManager questManager = megaQuests.getQuestManager();


        for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot(); i++){
            if (brokenBlock.getType().equals(Material.DIAMOND_ORE) && questGUI.getItem(i).getType().equals(Material.DIAMOND_ORE) && Quests.MINEDIAMONDORE.isEnabled() && !Quests.MINEDIAMONDORE.isCompleted()){
                questManager.checkCompletion(player, Quests.MINEDIAMONDORE,1);
            }
            if (brokenBlock.getType().equals(Material.IRON_ORE) && questGUI.getItem(i).getType().equals(Material.IRON_ORE) && Quests.MINEIRONORE.isEnabled() && !Quests.MINEIRONORE.isCompleted()){
                questManager.checkCompletion(player, Quests.MINEIRONORE, 1);
            }
        }


    }
}
