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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestManager {
    private File questProgressFile;
    Map<UUID, Map<Quests, Integer>> playerProgressMap;

    MegaQuests megaQuests;
    private int questGUIAmount = 5;

    public QuestManager(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
        playerProgressMap = new HashMap<>();
    }
    public void checkCompletion(Player player, Quests quests, int progress){
        addProgress(player.getUniqueId(), quests,progress);

        player.sendMessage("Checked Progress");
        if (megaQuests.getQuestManager().getProgress(player.getUniqueId(), quests) == quests.getTaskAmount()){
            BigDecimal rewardAmount = new BigDecimal(quests.getReward());

            try {
                megaQuests.getEssentials().getUser(player).giveMoney(rewardAmount);
            } catch (MaxMoneyException ex) {
                throw new RuntimeException(ex);
            }

            player.sendMessage(ChatColor.GREEN + "You completed " + quests.getDisplay() + ChatColor.GREEN + " and got $" + quests.getReward());
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

    public void createQuestsYml(){
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

        for (Quests quest : Quests.values()) {
            String questKey = quest.name();

            boolean enabled = config.getBoolean("quests." + questKey + ".enabled", true);
            quest.setEnabled(enabled);

            int maxTask = config.getInt("quests." + questKey + ".maxTask", quest.getMaxTask());
            quest.setMaxTask(maxTask);

            int minTask = config.getInt("quests." + questKey + ".minTask", quest.getMinTask());
            quest.setMinTask(minTask);

            int reward = config.getInt("quests." + questKey + ".reward", quest.getReward());
            quest.setReward(reward);


            config.set("quests." + questKey , quest.isEnabled());
            config.set("quests." + questKey + ".minTask", quest.getMinTask());
            config.set("quests." + questKey + ".maxTask", quest.getMaxTask());
            config.set("quests." + questKey + ".reward", quest.getReward());
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception if the file cannot be saved
        }
    }



    public void savePlayerQuestProgress(Player player) {
        UUID playerId = player.getUniqueId();

        FileConfiguration config = YamlConfiguration.loadConfiguration(questProgressFile);
        ConfigurationSection questProgressSection = config.getConfigurationSection("questProgress");
        if (questProgressSection == null) {
            questProgressSection = config.createSection("questProgress");
        }

        Map<Quests, Integer> questProgressMap = playerProgressMap.getOrDefault(playerId, new HashMap<>());
        for (Map.Entry<Quests, Integer> entry : questProgressMap.entrySet()) {
            questProgressSection.set(playerId.toString() + "." + entry.getKey().name(), entry.getValue());
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
            Map<Quests, Integer> questProgressMap = new HashMap<>();
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
        if (playerProgressMap != null){
            playerProgressMap.remove(playerId);
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
}
