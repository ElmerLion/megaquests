package com.elmer.megaquests.listeners.GUIs;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QuestGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        boolean isSameItem = ChatColor.translateAlternateColorCodes('&', e.getView().getTitle())
                .equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests");
        if (e.getCurrentItem() != null && isSameItem) {
            e.setCancelled(true);
        }
    }
}
