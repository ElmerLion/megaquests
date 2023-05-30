package com.elmer.megaquests.listeners;

import com.elmer.megaquests.MegaQuests;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    MegaQuests megaQuests;
    public JoinQuitListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent e){
        megaQuests.getQuestManager().loadPlayerQuestProgress(e.getPlayer());
    }
    @EventHandler
    public void onQuit (PlayerQuitEvent e){
        megaQuests.getQuestManager().savePlayerQuestProgress(e.getPlayer());
    }
}
