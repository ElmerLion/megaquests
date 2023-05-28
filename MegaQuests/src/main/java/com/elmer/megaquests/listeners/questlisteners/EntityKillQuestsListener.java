package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class EntityKillQuestsListener implements Listener {

    MegaQuests megaQuests;



    public EntityKillQuestsListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onEntityDeath (EntityDeathEvent e){
        if (e.getEntity().getKiller() instanceof Player){
            Player player = (Player) e.getEntity().getKiller();
            UUID playerId = player.getUniqueId();
            Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();

            //boolean progressAdded = megaQuests.getQuestManager().isProgressAdded();

            QuestManager questManager = megaQuests.getQuestManager();

            //progressAdded =  false;
            for (int i = megaQuests.getQuestManager().getQuestGUIAmount() + 6; i < megaQuests.getQuestManager().getQuestGUIAmount() - + 11; i++){
                if (e.getEntityType().equals(EntityType.SKELETON) && questGUI.getItem(i).getType().equals(Material.SKELETON_SKULL) && Quests.KILLSKELETON.isEnabled() && !Quests.KILLSKELETON.isCompleted()){
                    questManager.checkCompletion(player, Quests.KILLSKELETON, 1);
                }

                if (e.getEntityType().equals(EntityType.ZOMBIE) && questGUI.getItem(i).getType().equals(Material.ZOMBIE_HEAD) && !Quests.KILLZOMBIE.isCompleted()){
                    questManager.checkCompletion(player, Quests.KILLZOMBIE,1);
                }

                if (e.getEntityType().equals(EntityType.PLAYER) && questGUI.getItem(i).getType().equals(Material.PLAYER_HEAD) && !Quests.KILLZOMBIE.isCompleted()){
                    questManager.checkCompletion(player, Quests.KILLPLAYER, 1);
                }

                if (e.getEntityType().equals(EntityType.EVOKER) && questGUI.getItem(i).getType().equals(Material.TOTEM_OF_UNDYING) && !Quests.KILLEVOKER.isCompleted()){
                    questManager.checkCompletion(player, Quests.KILLEVOKER, 1);
                }

                if (e.getEntityType().equals(EntityType.BLAZE) && questGUI.getItem(i).getType().equals(Material.BLAZE_POWDER) && !Quests.KILLBLAZE.isCompleted()){
                    questManager.checkCompletion(player, Quests.KILLBLAZE, 1);
                }
            }

        }
    }
}
