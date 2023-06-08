package com.elmer.megaquests.commands;

import com.elmer.megaquests.managers.CooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetGlobalTimerCommand implements CommandExecutor {

    private final CooldownManager cooldownManager;
    public ResetGlobalTimerCommand (CooldownManager cooldownManager){
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        cooldownManager.cancelGlobalCooldown();

        cooldownManager.checkGlobalCooldown();

        commandSender.sendMessage("Successfully reset global timer.");

        return false;
    }
}
