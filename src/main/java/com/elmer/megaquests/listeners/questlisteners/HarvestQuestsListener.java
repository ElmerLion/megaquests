package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class HarvestQuestsListener implements Listener {

    private final MegaQuests megaQuests;
    private final QuestManager questManager;

    public HarvestQuestsListener(MegaQuests megaQuests, QuestManager questManager){
        this.megaQuests = megaQuests;
        this.questManager = questManager;
    }

    @EventHandler
    public void onHarvest (BlockBreakEvent e){

        if (e.getBlock().getBlockData() instanceof Ageable){
            Ageable crop = (Ageable) e.getBlock().getBlockData();
            if (crop.getAge() == crop.getMaximumAge()){
                Material cropType = e.getBlock().getType();
                QuestManager questManager = megaQuests.getQuestManager();
                Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();
                Player player = e.getPlayer();

                Map<Material, Quests> cropToQuestMap = new HashMap<>();
                cropToQuestMap.put(Material.WHEAT, Quests.HARVESTWHEAT);
                cropToQuestMap.put(Material.POTATOES, Quests.HARVESTPOTATO);
                cropToQuestMap.put(Material.CARROTS, Quests.HARVESTCARROT);
                cropToQuestMap.put(Material.BEETROOTS, Quests.HARVESTBEETROOT);

                if(cropToQuestMap.containsKey(cropType)) {
                    Quests associatedQuest = cropToQuestMap.get(cropType);
                    for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++) {
                        if (questGUI.getItem(i).getType().equals(associatedQuest.getItemDisplay()) &&
                                !questManager.checkCompletedEnabled(player, associatedQuest)){
                            questManager.checkCompletion(player, associatedQuest, 1);
                        }
                    }
                }

            }
        }
    }
}
