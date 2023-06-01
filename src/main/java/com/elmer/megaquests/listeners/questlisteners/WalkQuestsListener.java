package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class WalkQuestsListener implements Listener {
    MegaQuests megaQuests;
    public WalkQuestsListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onPlayerMove (PlayerMoveEvent e){
        Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();

        if (questGUI !=null){
            Location from = e.getFrom();
            Location to = e.getTo();
            Player player = e.getPlayer();
            QuestManager questManager = megaQuests.getQuestManager();

            Map<Biome, Quests> biomeToQuest = new HashMap<>();
            biomeToQuest.put(Biome.BADLANDS, Quests.VISITBADLANDS);
            biomeToQuest.put(Biome.DEEP_DARK, Quests.VISITDEEPDARK);
            biomeToQuest.put(Biome.SWAMP, Quests.VISITSWAMP);

            for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() +1; i++){
                if (questGUI.getItem(i).getType().equals(Material.LEATHER_BOOTS) && !questManager.checkCompletedEnabled(player, Quests.PLAYERWALK)){
                    if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
                        questManager.checkCompletion(player, Quests.PLAYERWALK, 1);
                    }
                }
                if (biomeToQuest.containsKey(player.getLocation().getBlock().getBiome())){
                    Quests associatedQuest = biomeToQuest.get(player.getLocation().getBlock().getBiome());
                    if (questGUI.getItem(i).getType().equals(associatedQuest.getItemDisplay())
                            && !questManager.checkCompletedEnabled(player, associatedQuest)){
                        questManager.checkCompletion(player, associatedQuest, 1);
                    }
                }
            }
        }

    }
}
