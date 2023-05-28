package com.elmer.megaquests.commands;

import com.elmer.megaquests.MegaQuests;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCooldownCommand implements CommandExecutor {
    MegaQuests megaQuests;
    public ResetCooldownCommand(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("megaquests.resetcooldown")){
                if (args.length == 1){
                    for (Player target : Bukkit.getOnlinePlayers()){
                        if (target.getName().equals(args[0])){
                            megaQuests.getCooldownManager().resetCooldown(target);
                            player.sendMessage(ChatColor.GREEN + "Successfully reset " + player.getName() + "'s cooldown on new quests!");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid Usage! Please use /resetcooldown <player>");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }

        }

        return false;
    }
}
