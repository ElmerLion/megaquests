package com.elmer.megaquests.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QuestGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests") && e.getCurrentItem() != null){
            e.setCancelled(true);
        }
    }
}
