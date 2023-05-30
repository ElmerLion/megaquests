package com.elmer.megaquests;

import com.earth2me.essentials.Essentials;
import com.elmer.megaquests.commands.QuestGUICommand;
import com.elmer.megaquests.commands.ResetCooldownCommand;
import com.elmer.megaquests.listeners.JoinQuitListener;
import com.elmer.megaquests.listeners.QuestGUIListener;
import com.elmer.megaquests.listeners.questlisteners.BlockBreakQuestsListener;
import com.elmer.megaquests.listeners.questlisteners.EntityKillQuestsListener;
import com.elmer.megaquests.managers.CooldownManager;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MegaQuests extends JavaPlugin {

    // GUI att välja quest (command för det)
    // Quests: Minea diamonds (Blockbreak lsitener), döda mobs (entitydeathlistener), fiska fiskar (fishing listener?)
    // Essentials API för rewards

    private final File dataFile = new File(getDataFolder(), "cooldowns.dat");
    private Essentials essentials;
    private CooldownManager cooldownManager;
    private QuestGUICommand questGUICommand;
    private QuestManager questManager;

    @Override
    public void onEnable() {

        essentials = Essentials.getPlugin(Essentials.class);
        cooldownManager = new CooldownManager(dataFile, this);
        questGUICommand = new QuestGUICommand(this, questManager);
        questManager = new QuestManager(this);

        new EntityKillQuestsListener(this);
        new BlockBreakQuestsListener(this);


        getCommand("quests").setExecutor(new QuestGUICommand(this, questManager));
        getCommand("resetcooldown").setExecutor(new ResetCooldownCommand(cooldownManager));


        Bukkit.getPluginManager().registerEvents(new EntityKillQuestsListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuestGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(questManager), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakQuestsListener(this), this);

        cooldownManager.resetAllCooldowns();

        questManager.setQuestProgressFile(this);

        questManager.createQuestsYml();
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public QuestGUICommand getQuestGUICommand() {
        return questGUICommand;
    }

    public Essentials getEssentials() {
        return essentials;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
