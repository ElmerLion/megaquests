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
            // Cooldown is not active, update the cooldown time
            long newCooldownEndTime = currentTime + TimeUnit.SECONDS.toMillis(60);
            cooldowns.put(playerId, newCooldownEndTime);
            saveCooldownsToFile();
            // Continue with the rest of your logic

            megaQuests.getQuestGUICommand().createQuestGUI(player);



        } else {
            // Cooldown is active, calculate the remaining time
            megaQuests.getQuestGUICommand().openQuestGUI(player);
            timeToWait = cooldownEndTime - currentTime;
            player.sendMessage(ChatColor.RED + "Wait for " + TimeUnit.MILLISECONDS.toSeconds(timeToWait) + ChatColor.RED + " seconds for quests to reset");
        }
    }

    private Map<UUID, Long> loadCooldownsFromFile() {
        Map<UUID, Long> cooldowns = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    UUID playerId = UUID.fromString(parts[0]);
                    long cooldownEndTime = Long.parseLong(parts[1]);
                    cooldowns.put(playerId, cooldownEndTime);
                }
            }
        } catch (IOException e) {
            // Handle exception if the file cannot be read
        }

        return cooldowns;
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

    public long getTimeToWait() {
        return TimeUnit.MILLISECONDS.toHours(timeToWait);
    }
}