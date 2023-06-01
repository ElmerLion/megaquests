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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                Map<Material, Quests> fishToQuest = new HashMap<>();
                fishToQuest.put(Material.COD, Quests.FISHCOD);
                fishToQuest.put(Material.SALMON, Quests.FISHSALMON);
                fishToQuest.put(Material.TROPICAL_FISH, Quests.FISHTROPICAL);
                fishToQuest.put(Material.PUFFERFISH, Quests.FISHPUFFERFISH);


                if (fishToQuest.containsKey(caughtMaterial)){
                    for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++ ){
                        Quests associatedQuest = fishToQuest.get(caughtMaterial);
                        if (questGUI.getItem(i).getType().equals(associatedQuest.getItemDisplay())
                                && !questManager.checkCompletedEnabled(player, associatedQuest)){
                            questManager.checkCompletion(player, associatedQuest, 1);
                        }
                    }
                }

                for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++ ){
                    if(treasures.contains(caughtMaterial) && questGUI.getItem(i).getType().equals(Material.GOLD_BLOCK) && !questManager.checkCompletedEnabled(player, Quests.FISHTREASURE)){
                        questManager.checkCompletion(player, Quests.FISHTREASURE, 1);
                    }
                }
            }

        }

    }
}
