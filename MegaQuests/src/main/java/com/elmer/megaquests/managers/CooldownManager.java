package com.elmer.megaquests.managers;

import com.elmer.megaquests.MegaQuests;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {
    private final Map<UUID, Long> cooldowns;
    private final File dataFile;
    private final MegaQuests megaQuests;
    private long timeToWait;
    private long resetTimer = 1440;


    public CooldownManager(File dataFile, MegaQuests megaQuests) {
        this.dataFile = dataFile;
        this.megaQuests = megaQuests;
        this.cooldowns = loadCooldownsFromFile();
    }

    public void checkCooldown(Player player) {
        UUID playerUUID = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        long cooldownEndTime = cooldowns.getOrDefault(playerUUID, 0L);

        if (currentTime >= cooldownEndTime) {
            // Cooldown is not active, update the cooldown time
            long newCooldownEndTime = currentTime + TimeUnit.MINUTES.toMillis(resetTimer);

            cooldowns.put(playerUUID, newCooldownEndTime);
            saveCooldownsToFile();

            // Continue with the rest of your logic
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
            // Handle exception if the file cannot be read
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
            // Handle exception if the file cannot be written
        }

    }

    public void resetCooldown(Player player) {
        cooldowns.remove(player.getUniqueId());
        saveCooldownsToFile();
    }

    public void resetAllCooldowns() {
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

    public Map<UUID, Long> getCooldowns() {
        return cooldowns;
    }
}