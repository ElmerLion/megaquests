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

import java.util.HashMap;
import java.util.Map;

public class BlockBreakQuestsListener implements Listener {
    private final MegaQuests megaQuests;
    private final QuestManager questManager;
    public BlockBreakQuestsListener(MegaQuests megaQuests, QuestManager questManager){
        this.megaQuests = megaQuests;
        this.questManager = questManager;
    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent e){
        Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();


        if (questGUI != null){
            Player player = e.getPlayer();
            Block brokenBlock = e.getBlock();
            Material brokenBlockType = brokenBlock.getType();

            Map<Material, Quests> blockToQuest = new HashMap<>();
            blockToQuest.put(Material.DIAMOND_ORE, Quests.MINEDIAMONDORE);
            blockToQuest.put(Material.IRON_ORE, Quests.MINEIRONORE);
            blockToQuest.put(Material.GOLD_ORE, Quests.MINEGOLDORE);
            blockToQuest.put(Material.COAL_ORE, Quests.MINECOALORE);
            blockToQuest.put(Material.OAK_LOG, Quests.MINEOAKWOOD);
            blockToQuest.put(Material.BIRCH_LOG, Quests.MINEBIRCHWOOD);
            blockToQuest.put(Material.DARK_OAK_LOG, Quests.MINEDARKOAKWOOD);
            blockToQuest.put(Material.JUNGLE_LOG, Quests.MINEJUNGLEWOOD);
            blockToQuest.put(Material.ACACIA_LOG, Quests.MINEACACIAWOOD);
            blockToQuest.put(Material.SPRUCE_LOG, Quests.MINESPRUCEWOOD);

            if (blockToQuest.containsKey(brokenBlockType)){
                Quests associatedQuest = blockToQuest.get(brokenBlockType);
                for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++){
                    if (questGUI.getItem(i).getType().equals(associatedQuest.getItemDisplay())
                            && !questManager.checkCompletedEnabled(player, associatedQuest)){
                        questManager.checkCompletion(player, associatedQuest, 1);
                }
            }
        }







        }


    }
}
