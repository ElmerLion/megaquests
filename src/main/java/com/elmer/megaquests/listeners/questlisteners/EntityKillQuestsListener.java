package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;

public class EntityKillQuestsListener implements Listener {

    MegaQuests megaQuests;
    public EntityKillQuestsListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onEntityDeath (EntityDeathEvent e){
        if (e.getEntity().getKiller() != null){
            Player player = (Player) e.getEntity().getKiller();
            Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();

            QuestManager questManager = megaQuests.getQuestManager();

            if (questGUI != null){
                for (int i = megaQuests.getQuestManager().getStartingSlot(); i < megaQuests.getQuestManager().getEndingSlot(); i++){
                    if (e.getEntityType().equals(EntityType.SKELETON) && questGUI.getItem(i).getType() == Quests.KILLSKELETON.getItemDisplay() && !questManager.checkCompletedEnabled(player, Quests.KILLSKELETON)){
                        questManager.checkCompletion(player, Quests.KILLSKELETON, 1);
                    }

                    if (e.getEntityType().equals(EntityType.ZOMBIE) && questGUI.getItem(i).getType() == Quests.KILLZOMBIE.getItemDisplay() && !questManager.checkCompletedEnabled(player, Quests.KILLZOMBIE)){
                        questManager.checkCompletion(player, Quests.KILLZOMBIE,1);
                    }

                    if (e.getEntityType().equals(EntityType.PLAYER) && questGUI.getItem(i).getType() == Quests.KILLPLAYER.getItemDisplay() && !questManager.checkCompletedEnabled(player, Quests.KILLPLAYER)){
                        questManager.checkCompletion(player, Quests.KILLPLAYER, 1);
                    }

                    if (e.getEntityType() == EntityType.CREEPER && questGUI.getItem(i).getType() == Quests.KILLCREEPER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLCREEPER)) {
                        questManager.checkCompletion(player, Quests.KILLCREEPER, 1);
                    }

                    if (e.getEntityType() == EntityType.SPIDER && questGUI.getItem(i).getType() == Quests.KILLSPIDER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLSPIDER)) {
                        questManager.checkCompletion(player, Quests.KILLSPIDER, 1);
                    }

                    if (e.getEntityType() == EntityType.ENDERMAN && questGUI.getItem(i).getType() == Quests.KILLENDERMAN.getItemDisplay()  && !questManager.checkCompletedEnabled(player,Quests.KILLENDERMAN)) {
                        questManager.checkCompletion(player, Quests.KILLENDERMAN, 1);
                    }
                    if (e.getEntityType() == EntityType.WITCH && questGUI.getItem(i).getType() == Quests.KILLWITCH.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLWITCH)) {
                        questManager.checkCompletion(player, Quests.KILLWITCH, 1);
                    }

                    if (e.getEntityType() == EntityType.CAVE_SPIDER && questGUI.getItem(i).getType() == Quests.KILLCAVESPIDER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLCAVESPIDER)) {
                        questManager.checkCompletion(player, Quests.KILLCAVESPIDER, 1);
                    }

                    if (e.getEntityType() == EntityType.BLAZE && questGUI.getItem(i).getType() == Quests.KILLBLAZE.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLBLAZE)) {
                        questManager.checkCompletion(player, Quests.KILLBLAZE, 1);
                    }

                    if (e.getEntityType() == EntityType.GHAST && questGUI.getItem(i).getType() == Quests.KILLGHAST.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLGHAST)) {
                        questManager.checkCompletion(player, Quests.KILLGHAST, 1);
                    }

                    if (e.getEntityType() == EntityType.MAGMA_CUBE && questGUI.getItem(i).getType() == Quests.KILLMAGMACUBE.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLMAGMACUBE)) {
                        questManager.checkCompletion(player, Quests.KILLMAGMACUBE, 1);
                    }

                    if (e.getEntityType() == EntityType.DROWNED && questGUI.getItem(i).getType() == Quests.KILLDROWNED.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLDROWNED)) {
                        questManager.checkCompletion(player, Quests.KILLDROWNED, 1);
                    }

                    if (e.getEntityType() == EntityType.PHANTOM && questGUI.getItem(i).getType() == Quests.KILLPHANTOM.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLPHANTOM)) {
                        questManager.checkCompletion(player, Quests.KILLPHANTOM, 1);
                    }
                    if (e.getEntityType() == EntityType.HUSK && questGUI.getItem(i).getType() == Quests.KILLHUSK.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLHUSK)) {
                        questManager.checkCompletion(player, Quests.KILLHUSK, 1);
                    }

                    if (e.getEntityType() == EntityType.STRAY && questGUI.getItem(i).getType() == Quests.KILLSTRAY.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLSTRAY)) {
                        questManager.checkCompletion(player, Quests.KILLSTRAY, 1);
                    }

                    if (e.getEntityType() == EntityType.WITHER_SKELETON && questGUI.getItem(i).getType() == Quests.KILLWITHERSKELETON.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLWITHERSKELETON)) {
                        questManager.checkCompletion(player, Quests.KILLWITHERSKELETON, 1);
                    }

                    if (e.getEntityType() == EntityType.EVOKER && questGUI.getItem(i).getType() == Quests.KILLEVOKER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLEVOKER)) {
                        questManager.checkCompletion(player, Quests.KILLEVOKER, 1);
                    }

                    if (e.getEntityType() == EntityType.VEX && questGUI.getItem(i).getType() == Quests.KILLVEX.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLVEX)) {
                        questManager.checkCompletion(player, Quests.KILLVEX, 1);
                    }

                    if (e.getEntityType() == EntityType.PILLAGER && questGUI.getItem(i).getType() == Quests.KILLPILLAGER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLPILLAGER)) {
                        questManager.checkCompletion(player, Quests.KILLPILLAGER, 1);
                    }

                    if (e.getEntityType() == EntityType.RAVAGER && questGUI.getItem(i).getType() == Quests.KILLRAVAGER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLRAVAGER)) {
                        questManager.checkCompletion(player, Quests.KILLRAVAGER, 1);
                    }

                    if (e.getEntityType() == EntityType.VINDICATOR && questGUI.getItem(i).getType() == Quests.KILLVINDICATOR.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLVINDICATOR)) {
                        questManager.checkCompletion(player, Quests.KILLVINDICATOR, 1);
                    }

                    if (e.getEntityType() == EntityType.ENDERMITE && questGUI.getItem(i).getType() == Quests.KILLENDERMITE.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLENDERMITE)) {
                        questManager.checkCompletion(player, Quests.KILLENDERMITE, 1);
                    }

                    if (e.getEntityType() == EntityType.GUARDIAN && questGUI.getItem(i).getType() == Quests.KILLGUARDIAN.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLGUARDIAN)) {
                        questManager.checkCompletion(player, Quests.KILLGUARDIAN, 1);
                    }

                    if (e.getEntityType() == EntityType.ELDER_GUARDIAN && questGUI.getItem(i).getType() == Quests.KILLELDERGUARDIAN.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLELDERGUARDIAN)) {
                        questManager.checkCompletion(player, Quests.KILLELDERGUARDIAN, 1);
                    }

                    if (e.getEntityType() == EntityType.SHULKER && questGUI.getItem(i).getType() == Quests.KILLSHULKER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLSHULKER)) {
                        questManager.checkCompletion(player, Quests.KILLSHULKER, 1);
                    }

                    if (e.getEntityType() == EntityType.SLIME && questGUI.getItem(i).getType() == Quests.KILLSLIME.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLSLIME)) {
                        questManager.checkCompletion(player, Quests.KILLSLIME, 1);
                    }
                    if (e.getEntityType() == EntityType.SILVERFISH && questGUI.getItem(i).getType() == Quests.KILLSILVERFISH.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLSILVERFISH)) {
                        questManager.checkCompletion(player, Quests.KILLSILVERFISH, 1);
                    }

                    if (e.getEntityType() == EntityType.ENDER_DRAGON && questGUI.getItem(i).getType() == Quests.KILLENDERDRAGON.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLENDERDRAGON)) {
                        questManager.checkCompletion(player, Quests.KILLENDERDRAGON, 1);
                    }

                    if (e.getEntityType() == EntityType.WITHER && questGUI.getItem(i).getType() == Quests.KILLWITHER.getItemDisplay() && !questManager.checkCompletedEnabled(player,Quests.KILLWITHER)){
                        questManager.checkCompletion(player, Quests.KILLWITHER, 1);
                    }

                }





            }

        }
    }
}
