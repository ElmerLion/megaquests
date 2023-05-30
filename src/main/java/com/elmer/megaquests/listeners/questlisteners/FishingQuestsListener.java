package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class FishingQuestsListener implements Listener {

    MegaQuests megaQuests;
    public FishingQuestsListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onFishing (PlayerFishEvent e){


        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) && e.getCaught() instanceof CraftItem){
            Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();

            if (questGUI != null){
                Player player = e.getPlayer();
                QuestManager questManager = megaQuests.getQuestManager();
                Material caughtMaterial = ((CraftItem) e.getCaught()).getItemStack().getType();

                List<Material> treasures = Arrays.asList(
                        Material.BOW,
                        Material.ENCHANTED_BOOK,
                        Material.NAME_TAG,
                        Material.NAUTILUS_SHELL,
                        Material.SADDLE
                );

                for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot(); i++ ){
                    if (caughtMaterial == Material.COD && questGUI.getItem(i).getType().equals(Material.COD) && !questManager.checkCompletedEnabled(player, Quests.FISHCOD)) {
                        questManager.checkCompletion(player, Quests.FISHCOD, 1);
                    }
                    if (caughtMaterial == Material.SALMON && questGUI.getItem(i).getType().equals(Material.SALMON) && !questManager.checkCompletedEnabled(player, Quests.FISHSALMON)) {
                        questManager.checkCompletion(player, Quests.FISHSALMON, 1);
                    }
                    if (caughtMaterial == Material.TROPICAL_FISH && questGUI.getItem(i).getType().equals(Material.TROPICAL_FISH) && !questManager.checkCompletedEnabled(player,Quests.FISHTROPICAL)) {
                        questManager.checkCompletion(player, Quests.FISHTROPICAL, 1);
                    }
                    if(treasures.contains(caughtMaterial) && questGUI.getItem(i).getType().equals(Material.GOLD_BLOCK) && !questManager.checkCompletedEnabled(player, Quests.FISHTREASURE)){
                        questManager.checkCompletion(player, Quests.FISHTREASURE, 1);
                    }
                }
            }

        }

    }
}
