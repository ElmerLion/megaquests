package com.elmer.megaquests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum Quests {

    MINEDIAMONDORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Diamonds"
            ,Material.DIAMOND_ORE, 100, 3, 20,  false, true, 0),
    KILLSKELETON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Skeletons"
            ,Material.SKELETON_SKULL, 100, 10,50,  false, true, 0 ),
    KILLZOMBIE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Zombies"
            ,Material.ZOMBIE_HEAD, 100, 10, 100, false, true, 0),
    KILLPLAYER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Players"
            ,Material.PLAYER_HEAD, 100, 1, 5,  false, true, 0),
    FISHING(ChatColor.GREEN.toString() + ChatColor.BOLD + "Go Fishing"
            , Material.TROPICAL_FISH, 100,5,20,  false, true, 0),
    KILLSPIDER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Spiders"
            , Material.SPIDER_EYE, 100, 5, 70,  false, true, 0),
    KILLEVOKER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Evokers"
            , Material.TOTEM_OF_UNDYING, 100, 1, 5,  false, true, 0),
    KILLBLAZE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Blazes"
            , Material.BLAZE_POWDER, 100, 5, 20,  false, true, 0),
    MINEIRONORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Iron Ore"
            ,Material.IRON_ORE, 100, 10, 150, false, true, 0),
    MINEGOLDORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Gold Ore"
            ,Material.GOLD_ORE, 100, 10, 150, false, true, 0),
    MINECOALORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Coal Ore"
            ,Material.COAL_ORE, 100, 10, 150, false, true, 0);

    private String display;
    private int reward;
    private int maxTask;
    private int minTask;
    private Material itemDisplay;
    private boolean completed;
    private boolean enabled;
    private int taskAmount;



    Quests(String display, Material itemDisplay, int reward, int minTask, int maxTask, boolean completed, boolean enabled, int taskAmount){
        this.display = display;
        this.reward = reward;
        this.maxTask = maxTask;
        this.minTask = minTask;
        this.itemDisplay = itemDisplay;
        this.completed = completed;
        this.enabled = enabled;
        this.taskAmount = taskAmount;
    }

    public String getDisplay() {return display;}
    public  int getReward(){return reward;}
    public int getMaxTask(){return maxTask;}
    public int getMinTask(){return minTask;}
    public Material getItemDisplay(){return itemDisplay;}

    public boolean isCompleted() {return completed;}
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public int getTaskAmount() {return taskAmount;}
    public void setTaskAmount(int amount) {taskAmount = amount; }
    public void resetTaskAmount () {taskAmount = 0;}

    //public static Map<UUID, Integer> getPlayerProgressMap() {return playerProgressMap;}
    public void setMaxTask(int amount){ maxTask = amount; }
    public void setMinTask(int amount) { minTask = amount; }
    public void setReward(int amount){reward = amount;}
}
