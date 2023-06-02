package com.elmer.megaquests;

import com.elmer.megaquests.commands.QuestSettingsGUI;
import com.elmer.megaquests.commands.QuestGUICommand;
import com.elmer.megaquests.listeners.JoinQuitListener;
import com.elmer.megaquests.listeners.GUIs.QuestSettingsGUIListener;
import com.elmer.megaquests.listeners.questlisteners.*;
import com.elmer.megaquests.listeners.GUIs.QuestGUIListener;
import com.elmer.megaquests.managers.CooldownManager;
import com.elmer.megaquests.managers.QuestManager;
import com.elmer.megaquests.commands.ResetCooldownCommand;
import com.elmer.megaquests.managers.QuestSettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.earth2me.essentials.Essentials;

import java.io.File;

public final class MegaQuests extends JavaPlugin {
    private Essentials essentials;
    private CooldownManager cooldownManager;
    private QuestGUICommand questGUICommand;
    private QuestManager questManager;
    private QuestSettingsManager questSettingsManager;
    private QuestSettingsGUI questSettingsGUI;
    private final File dataFile = new File(getDataFolder(), "cooldowns.dat");

    @Override
    public void onEnable() {

        getCommand("questsettings").setExecutor(new QuestSettingsGUI(this));
        getCommand("questsettings").setTabCompleter(new QuestSettingsCompleter());

        questManager = new QuestManager(this);
        questSettingsManager = new QuestSettingsManager(this);
        essentials = Essentials.getPlugin(Essentials.class);
        cooldownManager = new CooldownManager(dataFile, this);
        questGUICommand = new QuestGUICommand(this ,questManager);
        questSettingsGUI = new QuestSettingsGUI(this);

        getCommand("quests").setExecutor(questGUICommand);
        getCommand("resetcooldown").setExecutor(new ResetCooldownCommand(this));


        Bukkit.getPluginManager().registerEvents(new EntityKillQuestsListener(this, questManager), this);
        Bukkit.getPluginManager().registerEvents(new QuestGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakQuestsListener(this, questManager), this);
        Bukkit.getPluginManager().registerEvents(new EnchantingQuestsListener(this, questManager), this);
        Bukkit.getPluginManager().registerEvents(new WalkQuestsListener(this),this);
        Bukkit.getPluginManager().registerEvents(new FishingQuestsListener(this, questManager), this);
        Bukkit.getPluginManager().registerEvents(new QuestSettingsGUIListener(this, questManager), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CraftingQuestsListener(this, questManager), this);
        Bukkit.getPluginManager().registerEvents(new TameQuestsListener(this, questManager), this);
        Bukkit.getPluginManager().registerEvents(new HarvestQuestsListener(this,questManager), this);

        cooldownManager.resetAllCooldowns();

        questManager.completedQuests.clear();

        questManager.setQuestProgressFile(this);

        questManager.createQuestsYml();
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public QuestGUICommand getQuestGUICommand(){
        return questGUICommand;
    }

    public Essentials getEssentials() {
        return essentials;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public QuestSettingsManager getQuestSettingsManager() {return questSettingsManager;}

    @Override
    public void onDisable() {
        questManager.updateConfig();
    }
}
