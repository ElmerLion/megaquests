package com.elmer.megaquests.managers;

import com.elmer.megaquests.ItemBuilder;
import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import net.ess3.api.MaxMoneyException;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class QuestManager {
    private File questProgressFile;
    private CooldownManager cooldownManager;
    Map<UUID, Map<Quests, Integer>> playerProgressMap;
    private final Map<UUID, Map<Quests, Integer>> playerTaskAmountsMap = new HashMap<>();
    public Map<UUID, Set<Quests>> completedQuests = new HashMap<>();
    FileConfiguration config;
    File configFile;

    MegaQuests megaQuests;
    private int questGUIAmount = 5;
    private boolean isCooldownBased = true;

    public QuestManager(MegaQuests megaQuests, CooldownManager cooldownManager){
        this.megaQuests = megaQuests;
        this.cooldownManager = cooldownManager;
    }
    public void checkCompletion(Player player, Quests quests, int progress){
        UUID playerId = player.getUniqueId();
        addProgress(playerId, quests,progress);

        Map<Quests, Integer> playerTaskAmountMap = playerTaskAmountsMap.getOrDefault(playerId, new HashMap<>());
        int taskAmount = playerTaskAmountMap.getOrDefault(quests, 0);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(ChatColor.GREEN + quests.getDisplay() + ": " + getProgress(playerId, quests) + "/" + taskAmount));

        if (getProgress(player.getUniqueId(), quests) == taskAmount){
            BigDecimal rewardAmount = new BigDecimal(quests.getReward());

            try {
                megaQuests.getEssentials().getUser(player).giveMoney(rewardAmount);
            } catch (MaxMoneyException ex) {
                player.sendMessage(ChatColor.RED + "You have reached the max amount of money!");
            }

            player.sendMessage(ChatColor.GREEN + "You completed " + quests.getDisplay() + ChatColor.GREEN + " and got $" + quests.getReward());
            if (!completedQuests.containsKey(playerId)) {
                completedQuests.put(playerId, new HashSet<>());
            }
            completedQuests.get(playerId).add(quests);
        }


    }

    public void addProgress(UUID playerId, Quests quest, int amount) {
        Map<Quests, Integer> questProgressMap = playerProgressMap.computeIfAbsent(playerId, k -> new HashMap<>());
        questProgressMap.put(quest, questProgressMap.getOrDefault(quest, 0) + amount);
    }

    public int getProgress(UUID playerId, Quests quest) {
        Map<Quests, Integer> questProgressMap = playerProgressMap.getOrDefault(playerId, new HashMap<>());
        return questProgressMap.getOrDefault(quest, 0);
    }

    public void createQuestsYml(){
        playerProgressMap = new HashMap<>();
        File dataFolder = megaQuests.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        configFile = new File(dataFolder, "quests.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);

           long resetTimer = config.getLong("ResetTimer (in  minutes)", megaQuests.getCooldownManager().getResetTimer());
           megaQuests.getCooldownManager().setResetTimer(resetTimer);
           config.set("ResetTimer (in  minutes)", megaQuests.getCooldownManager().getResetTimer());

           int questGUIAmount = config.getInt("QuestsInGUIAmount", getQuestGUIAmount());
           setQuestGUIAmount(questGUIAmount);
           config.set("QuestsInGUIAmount", getQuestGUIAmount());

           boolean isIndividual = config.getBoolean("IndividualTimer", isCooldownBased());
           megaQuests.getQuestManager().setCooldownBased(isIndividual);
           config.set("IndividualTimer", isCooldownBased());


        for (Quests quest : Quests.values()) {
            String questKey = quest.name();

            boolean enabled = config.getBoolean("Quests." + questKey + ".enabled", quest.isEnabled());
            quest.setEnabled(enabled);

            String displayName = config.getString("Quests." + questKey + ".displayName", quest.getDisplay());
            quest.setDisplay(displayName);

            String material = config.getString("Quests." + questKey + ".itemDisplay", quest.getItemDisplay().toString());
            quest.setItemDisplay(Material.getMaterial(material));

            int maxTask = config.getInt("Quests." + questKey + ".maxTask", quest.getMaxTask());
            quest.setMaxTask(maxTask);

            int minTask = config.getInt("Quests." + questKey + ".minTask", quest.getMinTask());
            quest.setMinTask(minTask);

            int reward = config.getInt("Quests." + questKey + ".reward", quest.getReward());
            quest.setReward(reward);


            config.set("Quests." + questKey + ".enabled", quest.isEnabled());
            config.set("Quests." + questKey + ".displayName", quest.getDisplay());
            config.set("Quests." + questKey + ".itemDisplay", quest.getItemDisplay().toString());
            config.set("Quests." + questKey + ".minTask", quest.getMinTask());
            config.set("Quests." + questKey + ".maxTask", quest.getMaxTask());
            config.set("Quests." + questKey + ".reward", quest.getReward());
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateConfig(){


        config.set("ResetTimer (in  minutes)", megaQuests.getCooldownManager().getResetTimer());
        config.set("QuestsInGUIAmount", getQuestGUIAmount());
        config.set("IndividualTimer", isCooldownBased());

        for (Quests quest : Quests.values()){
            String questKey = quest.name();

            config.set("Quests." + questKey + ".enabled", quest.isEnabled());
            config.set("Quests." + questKey + ".displayName", quest.getDisplay());
            config.set("Quests." + questKey + ".itemDisplay", quest.getItemDisplay().toString());
            config.set("Quests." + questKey + ".minTask", quest.getMinTask());
            config.set("Quests." + questKey + ".maxTask", quest.getMaxTask());
            config.set("Quests." + questKey + ".reward", quest.getReward());
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void savePlayerQuestProgress(Player player) {
        UUID playerId = player.getUniqueId();

        FileConfiguration config = YamlConfiguration.loadConfiguration(questProgressFile);
        ConfigurationSection questProgressSection = config.getConfigurationSection("questProgress");
        ConfigurationSection taskAmountSection = config.getConfigurationSection("taskAmount");
        if (questProgressSection == null) {
            questProgressSection = config.createSection("questProgress");
        }

        if (taskAmountSection == null){
            taskAmountSection = config.createSection("taskAmount");
        }

        Map<Quests, Integer> questProgressMap = playerProgressMap.getOrDefault(playerId, new HashMap<>());
        for (Map.Entry<Quests, Integer> entry : questProgressMap.entrySet()) {
            questProgressSection.set(playerId + "." + entry.getKey().name(), entry.getValue());
        }

        Map<Quests, Integer> taskAmountMap = playerTaskAmountsMap.getOrDefault(playerId, new HashMap<>());
        for (Map.Entry<Quests, Integer> entry : taskAmountMap.entrySet()) {
            taskAmountSection.set(playerId + "." + entry.getKey().name(), entry.getValue());
        }

        try {
            config.save(questProgressFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerQuestProgress(Player player) {
        UUID playerId = player.getUniqueId();

        FileConfiguration config = YamlConfiguration.loadConfiguration(questProgressFile);
        ConfigurationSection questProgressSection = config.getConfigurationSection("questProgress");
        ConfigurationSection taskAmountSection = config.getConfigurationSection("taskAmount");

        if (questProgressSection != null) {
            EnumMap<Quests, Integer> questProgressMap = new EnumMap<>(Quests.class);
            ConfigurationSection playerSection = questProgressSection.getConfigurationSection(playerId.toString());
            if (playerSection != null) {
                for (String questName : playerSection.getKeys(false)) {
                    Quests quest = Quests.valueOf(questName);
                    int progress = playerSection.getInt(questName, 0);
                    questProgressMap.put(quest, progress);
                }
            }
            playerProgressMap.put(playerId, questProgressMap);
        }

        if (taskAmountSection != null) {
            Map<Quests, Integer> taskAmountMap = new HashMap<>();
            ConfigurationSection playerSection = taskAmountSection.getConfigurationSection(playerId.toString());
            if (playerSection != null) {
                for (String questName : playerSection.getKeys(false)) {
                    Quests quest = Quests.valueOf(questName);
                    int taskAmount = playerSection.getInt(questName, 0);
                    taskAmountMap.put(quest, taskAmount);
                }
            }
            playerTaskAmountsMap.put(playerId, taskAmountMap);
        }
    }

    public void resetPlayerQuestProgress(UUID playerId) {
        if (playerProgressMap != null){
            playerProgressMap.remove(playerId);
        }

        if (playerTaskAmountsMap != null) {
            playerTaskAmountsMap.remove(playerId);
        }


        FileConfiguration config = YamlConfiguration.loadConfiguration(questProgressFile);
        ConfigurationSection questProgressSection = config.getConfigurationSection("questProgress");
        if (questProgressSection != null) {
            questProgressSection.set(playerId.toString(), null);
            try {
                config.save(questProgressFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setQuestProgressFile(MegaQuests megaQuests) {

        String filePath = megaQuests.getDataFolder() + File.separator + "questProgress.yml";


        questProgressFile = new File(filePath);
        if (!questProgressFile.exists()) {
            try {
                questProgressFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public int getQuestGUIAmount() {
        return questGUIAmount;
    }

    public Map<UUID, Map<Quests, Integer>> getPlayerProgressMap() {
        return playerProgressMap;
    }

    public void setQuestGUIAmount(int questGUIAmount) {
        this.questGUIAmount = questGUIAmount;
        if (this.questGUIAmount >= 27){
            this.questGUIAmount = 27;
        }
        megaQuests.getCooldownManager().resetAllCooldowns();
    }
    public void addQuestGUIAmount(int amount){
        questGUIAmount += amount;
        if (questGUIAmount >= 27){
            questGUIAmount = 27;
        }
        if (questGUIAmount <= 1){
            questGUIAmount = 1;
        }
        megaQuests.getCooldownManager().resetAllCooldowns();
    }
    public int getStartingSlot(){
        int availableSlots = 27;
        int middleSlot = availableSlots / 2;

        int startingSlot = middleSlot - (getQuestGUIAmount() / 2);
        return startingSlot;
    }
    public int getEndingSlot(){
        int availableSlots = 27;
        int middleSlot = availableSlots / 2;

        int endingSlot = middleSlot + (getQuestGUIAmount() / 2);
        return endingSlot;
    }
    public boolean checkCompletedEnabled (Player player, Quests quest){
        UUID playerId = player.getUniqueId();

        if (completedQuests.containsKey(playerId)) {
            return completedQuests.get(playerId).contains(quest);
        }

        return false;

    }

    public Map<UUID, Map<Quests, Integer>> getPlayerTaskAmountsMap() {
        return playerTaskAmountsMap;
    }

    public boolean isCooldownBased() {return isCooldownBased;}
    public void setCooldownBased(boolean cooldownBased) {
        isCooldownBased = cooldownBased;

        if (!isCooldownBased){
            cooldownManager.resetAllCooldowns();
            cooldownManager.checkGlobalCooldown();
        }
        if (isCooldownBased){
            cooldownManager.resetAllCooldowns();
            cooldownManager.cancelGlobalCooldown();
        }
    }
}
