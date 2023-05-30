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
            for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot(); i++){
                if (questGUI.getItem(i).getType().equals(Material.LEATHER_BOOTS) && !questManager.checkCompletedEnabled(player, Quests.PLAYERWALK)){
                    if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
                        questManager.checkCompletion(player, Quests.PLAYERWALK, 1);
                    }
                }
                if (player.getLocation().getBlock().getBiome().equals(Biome.BADLANDS) && !questManager.checkCompletedEnabled(player, Quests.VISITBADLANDS)){
                    questManager.checkCompletion(player, Quests.VISITBADLANDS, 1);
                }
                if(player.getLocation().getBlock().getBiome().equals(Biome.SWAMP) && !questManager.checkCompletedEnabled(player, Quests.VISITSWAMP)){
                    questManager.checkCompletion(player, Quests.VISITSWAMP, 1);
                }
                if(player.getLocation().getBlock().getBiome().equals(Biome.DEEP_DARK) && !questManager.checkCompletedEnabled(player, Quests.VISITDEEPDARK)){
                    questManager.checkCompletion(player, Quests.VISITDEEPDARK, 1);
                }
            }
        }

    }
}
