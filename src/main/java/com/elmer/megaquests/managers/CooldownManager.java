package com.elmer.megaquests.managers;
import com.elmer.megaquests.MegaQuests;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {
    private final Map<UUID, Long> cooldowns;
    private final File dataFile;
    private MegaQuests megaQuests;
    private long timeToWait;
    private long resetTimer = 1440;


    public CooldownManager(File dataFile, MegaQuests megaQuests) {
        this.dataFile = dataFile;
        this.cooldowns = loadCooldownsFromFile();
        this.megaQuests = megaQuests;
    }

    public void checkCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        long cooldownEndTime = cooldowns.getOrDefault(playerId, 0L);

        if (currentTime >= cooldownEndTime) {
            long newCooldownEndTime = currentTime + TimeUnit.MINUTES.toMillis(resetTimer);
            cooldowns.put(playerId, newCooldownEndTime);
            saveCooldownsToFile();

            timeToWait = newCooldownEndTime - currentTime;
            setTimeToWait(timeToWait);

            megaQuests.getQuestGUICommand().createQuestGUI(player);



        } else {
            timeToWait = cooldownEndTime - currentTime;
            setTimeToWait(timeToWait);
            megaQuests.getQuestGUICommand().openQuestGUI(player);
        }
    }

    private Map<UUID, Long> loadCooldownsFromFile() {
        Map<UUID, Long> tempCooldownsMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    UUID playerId = UUID.fromString(parts[0]);
                    long cooldownEndTime = Long.parseLong(parts[1]);
                    tempCooldownsMap.put(playerId, cooldownEndTime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempCooldownsMap;
    }

    private void saveCooldownsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Map.Entry<UUID, Long> entry : cooldowns.entrySet()) {
                writer.write(entry.getKey().toString() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void resetCooldown(Player player) {
        cooldowns.remove(player.getUniqueId());
        saveCooldownsToFile();
    }
    public void resetAllCooldowns (){
        cooldowns.clear();
        saveCooldownsToFile();
    }

    public long getTimeToWait() {
        return timeToWait;
    }
    public void setTimeToWait(long amount) {
        timeToWait = amount;
    }

    public long getResetTimer() {
        return resetTimer;
    }

    public void setResetTimer(long minutes) {
        resetTimer = minutes;
    }
    public void addMinutesResetTimer(long minutes) {
        resetTimer += minutes;
        if (resetTimer <= 0){
            resetTimer = 0;
        }
    }
}