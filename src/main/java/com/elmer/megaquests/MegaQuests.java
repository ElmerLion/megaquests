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

    // GUI att välja quest (command för det)
    // Quests: Minea diamonds (Blockbreak lsitener), döda mobs (entitydeathlistener), fiska fiskar (fishing listener?)
    // Essentials API för rewards

    private Essentials essentials;
    private CooldownManager cooldownManager;
    private QuestGUICommand questGUICommand;
    private EntityKillQuestsListener entityKillQuestsListener;
    private QuestManager questManager;
    private QuestSettingsManager questSettingsManager;
    private BlockBreakQuestsListener blockBreakQuestsListener;
    private ResetCooldownCommand resetCooldownCommand;
    private EnchantingQuestsListener enchantingQuestsListener;
    private FishingQuestsListener fishingQuestsListener;
    private WalkQuestsListener walkQuestsListener;
    private JoinQuitListener joinQuitListener;
    private QuestSettingsGUIListener questSettingsGUIListener;
    private CraftingQuestsListener craftingQuestsListener;
    private QuestSettingsGUI questSettingsGUI;
    private File dataFile = new File(getDataFolder(), "cooldowns.dat");

    @Override
    public void onEnable() {

        getCommand("questsettings").setExecutor(new QuestSettingsGUI(this));
        getCommand("questsettings").setTabCompleter(new QuestSettingsCompleter());

        essentials = Essentials.getPlugin(Essentials.class);
        cooldownManager = new CooldownManager(dataFile, this);
        questGUICommand = new QuestGUICommand(this);
        questManager = new QuestManager(this);
        questSettingsManager = new QuestSettingsManager(this);
        questSettingsGUI = new QuestSettingsGUI(this);

        blockBreakQuestsListener = new BlockBreakQuestsListener(this);
        enchantingQuestsListener = new EnchantingQuestsListener(this);
        fishingQuestsListener = new FishingQuestsListener(this);
        walkQuestsListener =  new WalkQuestsListener(this);
        entityKillQuestsListener = new EntityKillQuestsListener(this);
        questSettingsGUIListener = new QuestSettingsGUIListener(this);
        joinQuitListener = new JoinQuitListener(this);
        craftingQuestsListener = new CraftingQuestsListener(this);


        getCommand("quests").setExecutor(new QuestGUICommand(this));
        getCommand("resetcooldown").setExecutor(new ResetCooldownCommand(this));


        Bukkit.getPluginManager().registerEvents(entityKillQuestsListener, this);
        Bukkit.getPluginManager().registerEvents(new QuestGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(joinQuitListener, this);
        Bukkit.getPluginManager().registerEvents(blockBreakQuestsListener, this);
        Bukkit.getPluginManager().registerEvents(enchantingQuestsListener, this);
        Bukkit.getPluginManager().registerEvents(walkQuestsListener,this);
        Bukkit.getPluginManager().registerEvents(fishingQuestsListener, this);
        Bukkit.getPluginManager().registerEvents(questSettingsGUIListener, this);
        Bukkit.getPluginManager().registerEvents(joinQuitListener, this);
        Bukkit.getPluginManager().registerEvents(craftingQuestsListener, this);

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
    public QuestSettingsGUI getQuestSettingsGUI() {return questSettingsGUI;}

    @Override
    public void onDisable() {
        questManager.updateConfig();
    }
}
