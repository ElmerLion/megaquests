package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftingQuestsListener implements Listener {

    MegaQuests megaQuests;

    public CraftingQuestsListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onCraft (CraftItemEvent e){
        if (e.getWhoClicked() instanceof Player){
            Player player = ((Player) e.getWhoClicked()).getPlayer();
            ItemStack craftedItem = e.getCurrentItem();

            Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();
            QuestManager questManager = megaQuests.getQuestManager();
            if (questGUI != null){
                for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot(); i++){
                    if (craftedItem.getType().equals(Material.BEACON) && questGUI.getItem(i).getType().equals(Quests.CRAFTBEACON.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTBEACON)){
                        questManager.checkCompletion(player, Quests.CRAFTBEACON,1);
                    }
                    if(craftedItem.getType().equals(Material.NETHERITE_INGOT) && questGUI.getItem(i).getType().equals(Quests.CRAFTNETHERITE.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTNETHERITE)){
                        questManager.checkCompletion(player, Quests.CRAFTNETHERITE, 1);
                    }
                    if (craftedItem.getType().equals(Material.SPYGLASS) && questGUI.getItem(i).getType().equals(Quests.CRAFTSPYGLASS.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTSPYGLASS)){
                        questManager.checkCompletion(player, Quests.CRAFTSPYGLASS, 1);
                    }
                }
            }



        }
    }
}
