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

import java.util.Objects;

public class EntityKillQuestsListener implements Listener {

    private final QuestManager questManager;
    private final MegaQuests megaQuests;

    public EntityKillQuestsListener(MegaQuests megaQuests) {
        this.megaQuests = megaQuests;
        this.questManager = megaQuests.getQuestManager();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (Objects.isNull(player)) {
            return;
        }
        Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();

        for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot(); i++) {
            if (event.getEntityType().equals(EntityType.SKELETON)
                    && questGUI.getItem(i).getType().equals(Material.SKELETON_SKULL)
                    && Quests.KILL_SKELETON.isEnabled()
                    && !Quests.KILL_SKELETON.isCompleted()) {
                questManager.checkCompletion(player, Quests.KILL_SKELETON, 1);
            } else if (event.getEntityType().equals(EntityType.ZOMBIE)
                    && questGUI.getItem(i).getType().equals(Material.ZOMBIE_HEAD)
                    && Quests.KILL_ZOMBIE.isEnabled()
                    && !Quests.KILL_ZOMBIE.isCompleted()) {
                questManager.checkCompletion(player, Quests.KILL_ZOMBIE, 1);
            } else if (event.getEntityType().equals(EntityType.PLAYER)
                    && questGUI.getItem(i).getType().equals(Material.PLAYER_HEAD)
                    && Quests.KILL_PLAYER.isEnabled()
                    && !Quests.KILL_ZOMBIE.isCompleted()) {
                questManager.checkCompletion(player, Quests.KILL_PLAYER, 1);
            } else if (event.getEntityType().equals(EntityType.EVOKER)
                    && questGUI.getItem(i).getType().equals(Material.TOTEM_OF_UNDYING)
                    && Quests.KILL_EVOKER.isEnabled()
                    && !Quests.KILL_EVOKER.isCompleted()) {
                questManager.checkCompletion(player, Quests.KILL_EVOKER, 1);
            } else if (event.getEntityType().equals(EntityType.BLAZE)
                    && questGUI.getItem(i).getType().equals(Material.BLAZE_POWDER)
                    && Quests.KILL_BLAZE.isEnabled()
                    && !Quests.KILL_BLAZE.isCompleted()) {
                questManager.checkCompletion(player, Quests.KILL_BLAZE, 1);
            }
        }

    }
}
