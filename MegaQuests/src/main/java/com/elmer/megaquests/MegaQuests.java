package com.elmer.megaquests;

import com.elmer.megaquests.commands.QuestGUICommand;
import com.elmer.megaquests.listeners.JoinQuitListener;
import com.elmer.megaquests.listeners.questlisteners.EntityKillQuestsListener;
import com.elmer.megaquests.listeners.QuestGUIListener;
import com.elmer.megaquests.managers.CooldownManager;
import com.elmer.megaquests.managers.QuestManager;
import com.elmer.megaquests.commands.ResetCooldownCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.earth2me.essentials.Essentials;

import java.io.File;
import java.io.IOException;

public final class MegaQuests extends JavaPlugin {

    // GUI att välja quest (command för det)
    // Quests: Minea diamonds (Blockbreak lsitener), döda mobs (entitydeathlistener), fiska fiskar (fishing listener?)
    // Essentials API för rewards

    private Essentials essentials;
    private CooldownManager cooldownManager;
    private QuestGUICommand questGUICommand;
    private EntityKillQuestsListener entityKillQuestsListener;
    private QuestManager questManager;
    private ResetCooldownCommand resetCooldownCommand;
    private File dataFile = new File(getDataFolder(), "cooldowns.dat");
    private int questAmount;

    @Override
    public void onEnable() {

        essentials = Essentials.getPlugin(Essentials.class);
        cooldownManager = new CooldownManager(dataFile, this);
        questGUICommand = new QuestGUICommand(this);
        entityKillQuestsListener = new EntityKillQuestsListener(this);
        questManager = new QuestManager(this);

        getCommand("quests").setExecutor(new QuestGUICommand(this));
        getCommand("resetcooldown").setExecutor(new ResetCooldownCommand(this));


        Bukkit.getPluginManager().registerEvents(new EntityKillQuestsListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuestGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this), this);



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



    @Override
    public void onDisable() {

    }
}
