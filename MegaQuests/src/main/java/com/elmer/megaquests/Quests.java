package com.elmer.megaquests;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Quests {

    MINE_DIAMOND_ORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Diamonds"
            , Material.DIAMOND_ORE, 100, 3, 20, false, true, 0),
    MINE_IRON_ORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Iron Ore"
            , Material.IRON_ORE, 100, 10, 150, false, true, 0),
    MINE_GOLD_ORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Gold Ore"
            , Material.GOLD_ORE, 100, 10, 150, false, true, 0),
    MINE_COAL_ORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Coal Ore"
            , Material.COAL_ORE, 100, 10, 150, false, true, 0),

    FISHING(ChatColor.GREEN.toString() + ChatColor.BOLD + "Go Fishing"
            , Material.TROPICAL_FISH, 100, 5, 20, false, true, 0),

    KILL_SKELETON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Skeletons"
            , Material.SKELETON_SKULL, 100, 10, 50, false, true, 0),
    KILL_ZOMBIE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Zombies"
            , Material.ZOMBIE_HEAD, 100, 10, 100, false, true, 0),
    KILL_PLAYER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Players"
            , Material.PLAYER_HEAD, 100, 1, 5, false, true, 0),
    KILL_SPIDER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Spiders"
            , Material.SPIDER_EYE, 100, 5, 70, false, true, 0),
    KILL_EVOKER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Evokers"
            , Material.TOTEM_OF_UNDYING, 100, 1, 5, false, true, 0),
    KILL_BLAZE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Blazes"
            , Material.BLAZE_POWDER, 100, 5, 20, false, true, 0);

    private final String display;
    private final Material itemDisplay;
    private int reward;
    private int maxTask;
    private int minTask;
    private boolean isCompleted;
    private boolean enabled;
    private int taskAmount;

    Quests(String display, Material itemDisplay, int reward,
           int minTask, int maxTask, boolean isCompleted,
           boolean enabled, int taskAmount) {
        this.display = display;
        this.reward = reward;
        this.maxTask = maxTask;
        this.minTask = minTask;
        this.itemDisplay = itemDisplay;
        this.isCompleted = isCompleted;
        this.enabled = enabled;
        this.taskAmount = taskAmount;
    }

    public String getDisplay() {
        return display;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int amount) {
        reward = amount;
    }

    public int getMaxTask() {
        return maxTask;
    }

    public void setMaxTask(int amount) {
        maxTask = amount;
    }

    public int getMinTask() {
        return minTask;
    }

    public void setMinTask(int amount) {
        minTask = amount;
    }

    public Material getItemDisplay() {
        return itemDisplay;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getTaskAmount() {
        return taskAmount;
    }

    public void setTaskAmount(int amount) {
        taskAmount = amount;
    }

    public void resetTaskAmount() {
        taskAmount = 0;
    }
}
