package com.elmer.megaquests.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QuestGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        boolean isSameItem = ChatColor.translateAlternateColorCodes('&', event.getView().getTitle())
                .equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests");

        if (event.getCurrentItem() != null && isSameItem) {
            event.setCancelled(true);
        }
    }
}
