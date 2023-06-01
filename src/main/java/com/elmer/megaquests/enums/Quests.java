package com.elmer.megaquests.enums;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum Quests {
    KILLZOMBIE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Zombies",
            Material.ZOMBIE_HEAD, 100, 10, 100, true, "hostilemobslaying"),
    KILLSKELETON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Skeletons",
            Material.SKELETON_SKULL, 150, 15, 150, true, "hostilemobslaying"),
    KILLCREEPER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Creepers",
            Material.CREEPER_HEAD, 80, 8, 50, true, "hostilemobslaying"),
    KILLSPIDER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Spiders",
            Material.SPIDER_SPAWN_EGG, 120, 12, 50, true, "hostilemobslaying"),
    KILLENDERMAN(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Endermen",
            Material.ENDERMAN_SPAWN_EGG, 200, 5, 20, true, "hostilemobslaying"),
    KILLWITCH(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Witches",
            Material.WITCH_SPAWN_EGG, 180, 2, 10, true, "hostilemobslaying"),
    KILLCAVESPIDER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Cave Spiders",
            Material.CAVE_SPIDER_SPAWN_EGG, 150, 10, 40, true, "hostilemobslaying"),
    KILLBLAZE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Blazes",
            Material.BLAZE_SPAWN_EGG, 250, 5, 40, true, "hostilemobslaying"),
    KILLGHAST(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Ghasts",
            Material.GHAST_SPAWN_EGG, 300, 1, 3, true, "hostilemobslaying"),
    KILLMAGMACUBE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Magma Cubes",
            Material.MAGMA_CUBE_SPAWN_EGG, 180, 1, 10, true, "hostilemobslaying"),
    KILLDROWNED(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Drowned",
            Material.DROWNED_SPAWN_EGG, 160, 10, 100, true, "hostilemobslaying"),
    KILLPHANTOM(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Phantoms",
            Material.PHANTOM_SPAWN_EGG, 220, 3, 15, true, "hostilemobslaying"),
    KILLHUSK(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Husks",
            Material.HUSK_SPAWN_EGG, 130, 10, 100, true, "hostilemobslaying"),
    KILLSTRAY(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Strays",
            Material.STRAY_SPAWN_EGG, 170, 10, 50, true, "hostilemobslaying"),
    KILLWITHERSKELETON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Wither Skeletons",
            Material.WITHER_SKELETON_SKULL, 200, 3, 20, true, "hostilemobslaying"),
    KILLEVOKER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Evokers",
            Material.EVOKER_SPAWN_EGG, 240, 1, 5, true, "hostilemobslaying"),
    KILLVEX(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Vexes",
            Material.VEX_SPAWN_EGG, 170, 5, 20, true, "hostilemobslaying"),
    KILLPILLAGER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Pillagers",
            Material.PILLAGER_SPAWN_EGG, 190, 5, 30, true, "hostilemobslaying"),
    KILLRAVAGER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Ravagers",
            Material.RAVAGER_SPAWN_EGG, 260, 1, 4, true, "hostilemobslaying"),
    KILLVINDICATOR(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Vindicators",
            Material.VINDICATOR_SPAWN_EGG, 210, 3, 25, true, "hostilemobslaying"),
    KILLENDERMITE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Endermites",
            Material.ENDERMITE_SPAWN_EGG, 140, 1, 7, true, "hostilemobslaying"),
    KILLGUARDIAN(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Guardians",
            Material.GUARDIAN_SPAWN_EGG, 230, 10, 30, true, "hostilemobslaying"),
    KILLELDERGUARDIAN(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Elder Guardians",
            Material.ELDER_GUARDIAN_SPAWN_EGG, 280, 1, 3, true, "hostilemobslaying"),
    KILLSHULKER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Shulkers",
            Material.SHULKER_SPAWN_EGG, 320, 4, 20, true, "hostilemobslaying"),
    KILLSLIME(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Slimes",
            Material.SLIME_SPAWN_EGG, 160, 10, 60, true, "hostilemobslaying"),
    KILLSILVERFISH(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Silverfish",
            Material.SILVERFISH_SPAWN_EGG, 120, 4, 15, true, "hostilemobslaying"),
    KILLENDERDRAGON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill the Ender Dragon",
            Material.DRAGON_HEAD, 500, 1, 1, true, "hostilemobslaying"),
    KILLWITHER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill the Wither",
            Material.NETHER_STAR, 400, 1, 1, true, "hostilemobslaying"),
    KILLPLAYER(ChatColor.GREEN.toString() + ChatColor.BOLD + "Kill Players",
            Material.PLAYER_HEAD, 100, 1, 10, true, "hostilemobslaying"),
    MINEIRONORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Iron Ore",
            Material.IRON_ORE, 100, 10, 150, true, "mineblocks"),
    MINEGOLDORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Gold Ore",
            Material.GOLD_ORE, 100, 10, 150, true, "mineblocks"),
    MINEDIAMONDORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Diamonds",
            Material.DIAMOND_ORE, 100, 3, 20, true, "mineblocks"),
    MINECOALORE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Coal Ore",
            Material.COAL_ORE, 100, 10, 150, true, "mineblocks"),
    MINEOAKWOOD(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Oak Logs",
            Material.OAK_LOG, 100, 10, 200, true, "mineblocks"),
    MINEBIRCHWOOD(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Birch Logs",
            Material.BIRCH_LOG, 100, 10, 200, true, "mineblocks"),
    MINEDARKOAKWOOD(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Dark Oak Logs",
            Material.DARK_OAK_LOG, 100, 10, 200, true, "mineblocks"),
    MINEACACIAWOOD(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Acacia Logs",
            Material.ACACIA_LOG, 100, 10, 200, true, "mineblocks"),
    MINEJUNGLEWOOD(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Jungle Logs",
            Material.JUNGLE_LOG, 100, 10, 200, true, "mineblocks"),
    MINESPRUCEWOOD(ChatColor.GREEN.toString() + ChatColor.BOLD + "Mine Spruce Logs",
            Material.SPRUCE_LOG, 100, 10, 200, true, "mineblocks"),
    ENCHANTDIAMONDTOOL(ChatColor.GREEN.toString() + ChatColor.BOLD + "Enchant Diamonds Tools"
            ,Material.DIAMOND_AXE, 100, 1, 1,  true, "enchanting"),
    ENCHANTIRONTOOL(ChatColor.GREEN.toString() + ChatColor.BOLD + "Enchant Iron Tools"
            ,Material.IRON_AXE, 100, 1, 5,  true, "enchanting"),
    ENCHANTGOLDTOOL(ChatColor.GREEN.toString() + ChatColor.BOLD + "Enchant Gold Tools"
            ,Material.GOLDEN_AXE, 100, 1, 5,  true, "enchanting"),
    PLAYERWALK(ChatColor.GREEN.toString() + ChatColor.BOLD + "Walk Blocks"
            , Material.LEATHER_BOOTS, 100, 500, 10000,  true, "playeraction"),
    FISHCOD(ChatColor.GREEN.toString() + ChatColor.BOLD + "Catch Cod",
            Material.COD, 100, 10, 60,  true, "fishing"),
    FISHSALMON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Catch Salmon",
            Material.SALMON, 100, 5, 50,  true, "fishing"),
    FISHTROPICAL(ChatColor.GREEN.toString() + ChatColor.BOLD + "Catch Tropical Fish",
            Material.TROPICAL_FISH, 100, 5, 20,  true, "fishing"),
    FISHPUFFERFISH(ChatColor.GREEN.toString() + ChatColor.BOLD + "Catch Pufferfish",
            Material.PUFFERFISH, 100, 5, 20, true, "fishing"),
    FISHTREASURE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Fish up Treasures",
            Material.GOLD_BLOCK, 100, 1, 10, true, "fishing"),
    VISITBADLANDS(ChatColor.GREEN.toString() + ChatColor.BOLD + "Visit Badlands/Mesa Biome",
            Material.RED_SAND, 100, 1,1, true, "biomevisit"),
    VISITDEEPDARK(ChatColor.GREEN.toString() + ChatColor.BOLD + "Visit the Deep Dark",
            Material.SCULK_SHRIEKER, 100, 1, 1, true, "biomevisit"),
    VISITSWAMP(ChatColor.GREEN.toString() + ChatColor.BOLD + "Visit Swamp",
            Material.SLIME_BALL, 100, 1, 1, true, "biomevisit"),
    VISITTHEEND (ChatColor.GREEN.toString() + ChatColor.BOLD + "Visit The End",
            Material.END_STONE, 1000, 1, 1, true, "biomevisit"),
    CRAFTBEACON(ChatColor.GREEN.toString() + ChatColor.BOLD + "Craft Beacon",
            Material.BEACON, 1000, 1, 2, true, "crafting"),
    CRAFTNETHERITE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Craft Netherite",
            Material.NETHERITE_INGOT, 1000, 1, 5, true, "crafting"),
    CRAFTSPYGLASS(ChatColor.GREEN.toString() + ChatColor.BOLD + "Craft Spyglass",
            Material.SPYGLASS, 100, 1, 3, true, "crafting"),
    CRAFTCONDUIT(ChatColor.GREEN.toString() + ChatColor.BOLD + "Craft Conduit",
            Material.CONDUIT, 2000, 1, 2, true, "crafting"),
    CRAFTANVIL(ChatColor.GREEN.toString() + ChatColor.BOLD + "Craft Anvil",
            Material.ANVIL, 1000, 1, 3, true, "crafting"),
    TAMEHORSE(ChatColor.GREEN.toString() + ChatColor.BOLD + "Tame Horse",
            Material.SADDLE, 100, 1, 5, true, "taming"),
    TAMEWOLF(ChatColor.GREEN.toString() + ChatColor.BOLD + "Tame Wolf",
            Material.BONE, 100, 1, 5, true, "taming"),
    HARVESTWHEAT(ChatColor.GREEN.toString() + ChatColor.BOLD + "Harvest Wheat",
            Material.WHEAT, 100, 10, 50, true, "harvesting"),
    HARVESTCARROT(ChatColor.GREEN.toString() + ChatColor.BOLD + "Harvest Carrots",
            Material.CARROT, 100, 10, 50, true, "harvesting"),
    HARVESTPOTATO(ChatColor.GREEN.toString() + ChatColor.BOLD + "Harvest Potatoes",
            Material.POTATO, 100, 10, 50, true, "harvesting"),
    HARVESTBEETROOT(ChatColor.GREEN.toString() + ChatColor.BOLD + "Harvest Beetroot",
            Material.BEETROOT, 100, 10, 50, true, "harvesting");

    private String display;
    private int reward;
    private int maxTask;
    private int minTask;
    private Material itemDisplay;
    private boolean enabled;
    private String category;



    Quests(String display, Material itemDisplay, int reward, int minTask, int maxTask, boolean enabled, String category){
        this.display = display;
        this.reward = reward;
        this.maxTask = maxTask;
        this.minTask = minTask;
        this.itemDisplay = itemDisplay;
        this.enabled = enabled;
        this.category = category;
    }

    public String getDisplay() {return display;}
    public  int getReward(){return reward;}
    public int getMaxTask(){return maxTask;}
    public int getMinTask(){return minTask;}
    public Material getItemDisplay(){return itemDisplay;}

    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public void setMaxTask(int amount){ maxTask = amount; }
    public void setMinTask(int amount) { minTask = amount; }
    public void setReward(int amount){reward = amount;}
    public String getCategory(){return category;}
    public void addReward (int amount) {
        reward += amount;
        if (reward <= 0){
            reward = 0;
        }
    }
    public void addMinTask (int amount) {
        minTask += amount;
        if (minTask <= 1){
            minTask = 1;
        }
    }
    public void addMaxTask (int amount) {
        maxTask += amount;
        if (maxTask <= 1){
            maxTask = 1;
        }
    }
}
