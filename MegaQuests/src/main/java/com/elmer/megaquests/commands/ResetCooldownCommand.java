package com.elmer.megaquests.commands;

import com.elmer.megaquests.managers.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ResetCooldownCommand implements CommandExecutor {
    private final CooldownManager cooldownManager;

    public ResetCooldownCommand(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return false;
        }

        if (!player.hasPermission("megaquests.resetcooldown")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Invalid Usage! Please use /resetcooldown <player>");
            return false;
        }

        String targetPlayerName = args[0];
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        if (Objects.isNull(targetPlayer)) {
            player.sendMessage(ChatColor.RED + "Invalid Player!");
            return false;
        }
        cooldownManager.resetCooldown(targetPlayer);
        player.sendMessage(ChatColor.GREEN + "Successfully reset " + player.getName() + "'s cooldown on new quests!");

        return true;
    }
}
