package com.elmer.megaquests;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public enum Quests {

    MINEDIAMONDORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Diamonds"
            ,Material.DIAMOND, 100, 3, 20, 0, false, true, 0),
    KILLSKELETON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Skeletons"
            ,Material.SKELETON_SKULL, 100, 10,50, 0, false, true, 0 ),
    KILLZOMBIE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Zombies"
            ,Material.ZOMBIE_HEAD, 100, 10, 100, 0,false, true, 0),
    KILLPLAYER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Players"
            ,Material.PLAYER_HEAD, 100, 1, 5, 0, false, true, 0),
    FISHING(ChatColor.GREEN.toString() + ChatColor.BOLD + "Go Fishing"
            , Material.TROPICAL_FISH, 100,5,20, 0, false, true, 0),
    KILLSPIDER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Spiders"
            , Material.SPIDER_EYE, 100, 5, 70, 0, false, true, 0),
    KILLEVOKER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Evokers"
            , Material.TOTEM_OF_UNDYING, 100, 1, 5, 0, false, true, 0),
    KILLBLAZE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Blazes"
            , Material.BLAZE_POWDER, 100, 5, 20, 0, false, true, 0);

    private String display;
    private int reward;
    private int maxTask;
    private int minTask;
    private Material itemDisplay;
    /*private String description1;
    private String description2;*/
    private int progress;
    private boolean completed;
    private boolean enabled;
    private int taskAmount;
    private Map<UUID, Integer> playerProgressMap;



    Quests(String display, Material itemDisplay, int reward, int minTask, int maxTask, int progress, boolean completed, boolean enabled, int taskAmount){
        this.display = display;
        this.reward = reward;
        this.maxTask = maxTask;
        this.minTask = minTask;
        this.itemDisplay = itemDisplay;
        //this.description1 = description1;
        //this.description2 = description2;
        this.progress = progress;
        this.completed = completed;
        this.enabled = enabled;
        this.taskAmount = taskAmount;
    }

    public String getDisplay() {return display;}
    public  int getReward(){return reward;}
    public int getMaxTask(){return maxTask;}
    public int getMinTask(){return minTask;}
    public Material getItemDisplay(){return itemDisplay;}

    //public String getDescription1() {return description1;}

    //public String getDescription2() {return description2;}

    public int getProgress(UUID playerId) {return playerProgressMap.getOrDefault(playerId, 0);}
    public void addProgress(UUID playerId, int amount) {
        int currentProgress = playerProgressMap.getOrDefault(playerId, 0);
        playerProgressMap.put(playerId, currentProgress + amount);
    }
    public void resetProgress() {progress = 0;}

    public boolean isCompleted() {return completed;}
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public int getTaskAmount() {return taskAmount;}
    public void setTaskAmount(int amount) {taskAmount = amount; }
    public void resetTaskAmount () {taskAmount = 0;}
}
