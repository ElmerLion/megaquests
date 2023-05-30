package com.elmer.megaquests.commands;

import com.elmer.megaquests.MegaQuests;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class QuestSettingsGUI implements CommandExecutor {

    MegaQuests megaQuests;
    public QuestSettingsGUI(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("megaquests.questsettings")){
                if (args.length == 1){
                    megaQuests.getQuestSettingsManager().createGUIWithCategory(player, args[0]);
                } else {
                    player.sendMessage(ChatColor.RED+ "Invalid Usage! Use /questsettings <category>");
                }

            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to do this!");
            }


        }

        return false;
    }

}
