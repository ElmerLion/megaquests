package com.elmer.megaquests.managers;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.Quests;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class QuestManager {
    private final Map<UUID, Map<Quests, Integer>> playerProgressMap = new HashMap<>();
    private final MegaQuests megaQuests;
    private File questProgressFile;
    private int questGUIAmount = 5;

    public QuestManager(MegaQuests megaQuests) {
        this.megaQuests = megaQuests;
    }

    public void checkCompletion(Player player, Quests quests, int progress) {
        addProgress(player.getUniqueId(), quests, progress);

        player.sendMessage("Checked Progress");
        if (megaQuests.getQuestManager().getProgress(player.getUniqueId(), quests) == quests.getTaskAmount()) {
            BigDecimal rewardAmount = new BigDecimal(quests.getReward());

            try {
                megaQuests.getEssentials().getUser(player).giveMoney(rewardAmount);
                player.sendMessage(ChatColor.GREEN + "You completed " + quests.getDisplay() + ChatColor.GREEN + " and got $" + quests.getReward());
            } catch (MaxMoneyException exception) {
                player.sendMessage("You have reached the maximum amount of money!");
            }

            quests.setCompleted(true);
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

    public void createQuestsYml() {
        File dataFolder = megaQuests.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File configFile = new File(dataFolder, "quests.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        // https://yaml.org/spec/1.2.2/
        // Do not use tabs/space for indentation, please.
        long resetTimer = config.getLong("ResetTimer", megaQuests.getCooldownManager().getResetTimer());
        megaQuests.getCooldownManager().setResetTimer(resetTimer);
        config.set("ResetTimer", megaQuests.getCooldownManager().getResetTimer());

        updateQuestGUIAmountFromConfig(config.getInt("QuestsInGUIAmount", questGUIAmount));
        config.set("QuestsInGUIAmount", questGUIAmount);


        for (Quests quest : Quests.values()) {
            String configPath = "Quests." + quest.name();

            boolean enabled = config.getBoolean(configPath + ".enabled", true);
            quest.setEnabled(enabled);

            int maxTask = config.getInt(configPath + ".maxTask", quest.getMaxTask());
            quest.setMaxTask(maxTask);

            int minTask = config.getInt(configPath + ".minTask", quest.getMinTask());
            quest.setMinTask(minTask);

            int reward = config.getInt(configPath + ".reward", quest.getReward());
            quest.setReward(reward);


            config.set(configPath + ".enabled", quest.isEnabled());
            config.set(configPath + ".minTask", quest.getMinTask());
            config.set(configPath + ".maxTask", quest.getMaxTask());
            config.set(configPath + ".reward", quest.getReward());
        }
        try {
            config.save(configFile);
        } catch (IOException exception) {
            Bukkit.getLogger().log(Level.SEVERE, String.format("Could not save config.yml to %s", configFile), exception);
        }
    }


    public void savePlayerQuestProgress(Player player) {
        UUID playerUUID = player.getUniqueId();

        FileConfiguration config = YamlConfiguration.loadConfiguration(questProgressFile);
        ConfigurationSection questProgressSection = config.getConfigurationSection("questProgress");
        if (questProgressSection == null) {
            questProgressSection = config.createSection("questProgress");
        }

        Map<Quests, Integer> questProgressMap = playerProgressMap.getOrDefault(playerUUID, new HashMap<>());
        for (Map.Entry<Quests, Integer> entry : questProgressMap.entrySet()) {
            questProgressSection.set(playerUUID + "." + entry.getKey().name(), entry.getValue());
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
    }

    public void resetPlayerQuestProgress(UUID playerId) {
        playerProgressMap.remove(playerId);


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

    public Map<UUID, Map<Quests, Integer>> getPlayerProgressMap() {
        return playerProgressMap;
    }

    public void updateQuestGUIAmountFromConfig(int questGUIAmount) {
        this.questGUIAmount = questGUIAmount;
    }

    public int getStartingSlot() {
        int availableSlots = 27;
        int middleSlot = availableSlots / 2;

        return middleSlot - (questGUIAmount / 2);
    }

    public int getEndingSlot() {
        int availableSlots = 27;
        int middleSlot = availableSlots / 2;

        return middleSlot + (questGUIAmount / 2);
    }
}
