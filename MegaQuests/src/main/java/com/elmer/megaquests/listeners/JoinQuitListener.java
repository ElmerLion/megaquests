package com.elmer.megaquests.listeners;

import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private final QuestManager questManager;

    public JoinQuitListener(QuestManager questManager) {
        this.questManager = questManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        questManager.loadPlayerQuestProgress(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        questManager.savePlayerQuestProgress(e.getPlayer());
    }
}
