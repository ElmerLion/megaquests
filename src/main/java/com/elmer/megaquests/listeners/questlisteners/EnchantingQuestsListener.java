package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantingQuestsListener implements Listener {
    private final MegaQuests megaQuests;
    private final QuestManager questManager;
    public EnchantingQuestsListener(MegaQuests megaQuests, QuestManager questManager){
        this.megaQuests = megaQuests;
        this.questManager = questManager;
    }

    @EventHandler
    public void onEnchant (EnchantItemEvent e){

        Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI(e.getEnchanter());

        if (questGUI != null){
            Player player = e.getEnchanter();
            ItemStack enchantedItem = e.getItem();
            QuestManager questManager = megaQuests.getQuestManager();

            Material enchantedItemType = enchantedItem.getType();
            boolean isDiamondTool = enchantedItemType == Material.DIAMOND_AXE
                    || enchantedItemType == Material.DIAMOND_HOE
                    || enchantedItemType == Material.DIAMOND_SWORD
                    || enchantedItemType == Material.DIAMOND_PICKAXE
                    || enchantedItemType == Material.DIAMOND_SHOVEL;

            boolean isIronTool = enchantedItemType == Material.IRON_AXE
                    || enchantedItemType == Material.IRON_HOE
                    || enchantedItemType == Material.IRON_SWORD
                    || enchantedItemType == Material.IRON_PICKAXE
                    || enchantedItemType == Material.IRON_SHOVEL;

            boolean isGoldTool = enchantedItemType == Material.GOLDEN_AXE
                    || enchantedItemType == Material.GOLDEN_HOE
                    || enchantedItemType == Material.GOLDEN_SWORD
                    || enchantedItemType == Material.GOLDEN_PICKAXE
                    || enchantedItemType == Material.GOLDEN_SHOVEL;

            for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++){
                if (isDiamondTool && isDiamondTool(questGUI.getItem(i).getType())
                        && !questManager.checkCompletedEnabled(player, Quests.ENCHANTDIAMONDTOOL)){
                    questManager.checkCompletion(player, Quests.ENCHANTDIAMONDTOOL, 1);
                }
                if(isIronTool && isIronTool(questGUI.getItem(i).getType())
                        && !questManager.checkCompletedEnabled(player, Quests.ENCHANTIRONTOOL)){
                    questManager.checkCompletion(player, Quests.ENCHANTIRONTOOL, 1);
                }
                if(isGoldTool && isGoldTool(questGUI.getItem(i).getType())
                        && !questManager.checkCompletedEnabled(player, Quests.ENCHANTGOLDTOOL)){
                    questManager.checkCompletion(player, Quests.ENCHANTGOLDTOOL, 1);
                }

            }
        }


    }
    private boolean isDiamondTool(Material material) {
        return material == Material.DIAMOND_AXE
                || material == Material.DIAMOND_HOE
                || material == Material.DIAMOND_SWORD
                || material == Material.DIAMOND_PICKAXE
                || material == Material.DIAMOND_SHOVEL;
    }
    private boolean isIronTool(Material material) {
        return material == Material.IRON_AXE
                || material == Material.IRON_HOE
                || material == Material.IRON_SWORD
                || material == Material.IRON_PICKAXE
                || material == Material.IRON_SHOVEL;
    }

    private boolean isGoldTool(Material material) {
        return material == Material.GOLDEN_AXE
                || material == Material.GOLDEN_HOE
                || material == Material.GOLDEN_SWORD
                || material == Material.GOLDEN_PICKAXE
                || material == Material.GOLDEN_SHOVEL;
    }
}
