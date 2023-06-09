package com.elmer.megaquests.listeners.GUIs;

import com.elmer.megaquests.ItemBuilder;
import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.CooldownManager;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class QuestSettingsGUIListener implements Listener {
    private final MegaQuests megaQuests;
    private final QuestManager questManager;
    public QuestSettingsGUIListener(MegaQuests megaQuests, QuestManager questManager){
        this.megaQuests = megaQuests;
        this.questManager = questManager;
    }

    private final Map<UUID, Quests> playerClickedQuestMap = new HashMap<>();

    @EventHandler
    public void onInventoryClick (InventoryClickEvent e){

        boolean isSameTitle = ChatColor.translateAlternateColorCodes('&', e.getView().getTitle())
                .equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Quests" + ChatColor.GRAY + " - Change Settings");

        if(isSameTitle && e.getCurrentItem() != null){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            playerClickedQuestMap.remove(player.getUniqueId());




            for (Quests quest: Quests.values()){
                if (quest.getDisplay().equals(clickedItem.getItemMeta().getDisplayName())){
                    playerClickedQuestMap.put(player.getUniqueId(), quest);
                    break;
                }
            }

            click(player);
            buildClickedQuestInv(player);

        }
        if (playerClickedQuestMap.get(e.getWhoClicked().getUniqueId()) != null){

            boolean isQuestView = ChatColor.translateAlternateColorCodes('&',
                    e.getView().getTitle()).equals(playerClickedQuestMap.get(e.getWhoClicked().getUniqueId()).getDisplay()
                    + ChatColor.GRAY + " -  Settings");
            if (isQuestView && e.getCurrentItem() !=  null){
                e.setCancelled(true);


                ItemStack clickedItem = e.getCurrentItem();
                Player player = (Player) e.getWhoClicked();
                ClickType click = e.getClick();
                String displayName = clickedItem.getItemMeta().getDisplayName();
                Quests clickedQuest = playerClickedQuestMap.get(player.getUniqueId());


                if (displayName.equals(ChatColor.GOLD + "Enable/Disable")){
                    if (click.isRightClick()){
                        clickedQuest.setEnabled(false);
                    }
                    if (click.isLeftClick()){
                        clickedQuest.setEnabled(true);
                    }
                    click(player);
                    buildClickedQuestInv(player);
                }
                if (displayName.equals(ChatColor.GOLD + "Change Reward")){
                    if (click.isRightClick() && !click.isShiftClick()){
                        clickedQuest.addReward(-10);
                    }
                    if (click.isRightClick() && click.isShiftClick()){
                        clickedQuest.addReward(-100);
                    }
                    if (click.isLeftClick() && !click.isShiftClick()){
                        clickedQuest.addReward(10);
                    }
                    if (click.isLeftClick() && click.isShiftClick()){
                        clickedQuest.addReward(100);
                    }
                    click(player);
                    buildClickedQuestInv(player);
                }
                if (displayName.equals(ChatColor.GOLD + "Change Minimum amount of objectives")){
                    if (click.isRightClick() && !click.isShiftClick()){
                        clickedQuest.addMinTask(-1);
                    }
                    if (click.isRightClick() && click.isShiftClick()){
                        clickedQuest.addMinTask(-10);
                    }
                    if (click.isLeftClick() && !click.isShiftClick()){
                        clickedQuest.addMinTask(1);
                    }
                    if (click.isLeftClick() && click.isShiftClick()){
                        clickedQuest.addMinTask(10);
                    }
                    click(player);
                    buildClickedQuestInv(player);
                }
                if (displayName.equals(ChatColor.GOLD + "Change Maximum amount of objectives")){
                    if (click.isRightClick() && !click.isShiftClick()){
                        clickedQuest.addMaxTask(-1);
                    }
                    if (click.isRightClick() && click.isShiftClick()){
                        clickedQuest.addMaxTask(-10);
                    }
                    if (click.isLeftClick() && !click.isShiftClick()){
                        clickedQuest.addMaxTask(1);
                    }
                    if (click.isLeftClick() && click.isShiftClick()){
                        clickedQuest.addMaxTask(10);
                    }
                    click(player);
                    buildClickedQuestInv(player);
                }
                if (displayName.equals(ChatColor.GOLD + "Amount of generated quests in /quests" + ChatColor.RED + " IF CHANGED, RESETS ALL QUESTS")){
                    if (click.isRightClick()){
                        questManager.addQuestGUIAmount(-1);
                    }
                    if (click.isLeftClick()){
                        questManager.addQuestGUIAmount(1);
                    }
                    click(player);
                    buildClickedQuestInv(player);
                }
                if (displayName.equals(ChatColor.GOLD + "Change time to reset quests, takes effect after current timer ends")){
                    CooldownManager cooldownManager = megaQuests.getCooldownManager();
                    if (click.isRightClick() && !click.isShiftClick()){
                        cooldownManager.addMinutesResetTimer(-120);
                    }
                    if (click.isRightClick() && click.isShiftClick()){
                        cooldownManager.addMinutesResetTimer(-30);
                    }
                    if (click.isLeftClick() && !click.isShiftClick()){
                        cooldownManager.addMinutesResetTimer(120);
                    }
                    if (click.isLeftClick() && click.isShiftClick()){
                        cooldownManager.addMinutesResetTimer(30);
                    }
                    click(player);
                    buildClickedQuestInv(player);
                }
                if (displayName.equals(ChatColor.GOLD + "Cooldown Type")){
                    if (click.isLeftClick()){
                        questManager.setCooldownBased(true);
                    }
                    if (click.isRightClick()){
                        questManager.setCooldownBased(false);
                    }
                    click(player);
                    buildClickedQuestInv(player);
                }
                questManager.updateConfig();
            }
        }
    }


    public void buildClickedQuestInv(Player player){
        Quests clickedQuest = playerClickedQuestMap.get(player.getUniqueId());
        Inventory clickedQuestInv = Bukkit.createInventory(player, 9, clickedQuest.getDisplay() + ChatColor.GRAY + " -  Settings");

        ItemStack enableDisable = new ItemBuilder(Material.REDSTONE)
                .withDisplayName(ChatColor.GOLD + "Enable/Disable").
                withLore(ChatColor.GREEN + "Current: " + clickedQuest.isEnabled(), " "
                        ,ChatColor.YELLOW + "LEFT-CLICK: Enable", ChatColor.YELLOW + "RIGHT-CLICK: Disable").build();

        ItemStack changeReward = new ItemBuilder(Material.DIAMOND)
                .withDisplayName(ChatColor.GOLD + "Change Reward")
                .withLore(ChatColor.GREEN + "Current: " + clickedQuest.getReward(), " "
                        ,ChatColor.YELLOW + "LEFT-CLICK: Add $10", ChatColor.YELLOW + "LEFT-CLICK + SHIFT: Add $100"
                        ,ChatColor.YELLOW + "RIGHT-CLICK: Remove $10", ChatColor.YELLOW + "RIGHT-CLICK + SHIFT: Remove $100").build();

        ItemStack changeMinTask = new ItemBuilder(Material.IRON_INGOT)
                .withDisplayName(ChatColor.GOLD + "Change Minimum amount of objectives")
                .withLore(ChatColor.GREEN + "Current: " + clickedQuest.getMinTask(), " "
                        ,ChatColor.YELLOW + "LEFT-CLICK: Add 1", ChatColor.YELLOW + "LEFT-CLICK + SHIFT: Add 10"
                        ,ChatColor.YELLOW + "RIGHT-CLICK: Remove 1", ChatColor.YELLOW + "RIGHT-CLICK + SHIFT: Remove 10").build();

        ItemStack changeMaxTask = new ItemBuilder(Material.GOLD_INGOT)
                .withDisplayName(ChatColor.GOLD + "Change Maximum amount of objectives")
                .withLore(ChatColor.GREEN + "Current: " + clickedQuest.getMaxTask(), " "
                        ,ChatColor.YELLOW + "LEFT-CLICK: Add 1", ChatColor.YELLOW + "LEFT-CLICK + SHIFT: Add 10"
                        ,ChatColor.YELLOW + "RIGHT-CLICK: Remove 1", ChatColor.YELLOW + "RIGHT-CLICK + SHIFT: Remove 10").build();

        ItemStack changeQuestAmount = new ItemBuilder(Material.BLUE_BANNER)
                .withDisplayName(ChatColor.GOLD + "Amount of generated quests in /quests" + ChatColor.RED + " IF CHANGED, RESETS ALL QUESTS")
                .withLore(ChatColor.GREEN + "Current: " + megaQuests.getQuestManager().getQuestGUIAmount(), " "
                        ,ChatColor.YELLOW + "LEFT-CLICK: Add 1", ChatColor.YELLOW + "RIGHT-CLICK: Remove 1").build();

        ItemStack changeResetTimer = new ItemBuilder(Material.CLOCK)
                .withDisplayName(ChatColor.GOLD + "Change time to reset quests, takes effect after current timer ends")
                .withLore(ChatColor.GREEN + "Current (Minutes): " + megaQuests.getCooldownManager().getResetTimer()
                        ,ChatColor.GREEN + "Current (Hours): " + TimeUnit.MINUTES.toHours(megaQuests.getCooldownManager().getResetTimer()), " "
                        ,ChatColor.YELLOW + "LEFT-CLICK: Add 2 Hours", ChatColor.YELLOW + "RIGHT-CLICK: Remove 2 Hours"
                        ,ChatColor.YELLOW + "LEFT-CLICK + SHIFT: Add 30 Minutes", ChatColor.YELLOW + "RIGHT-CLICK + SHIFT: Remove 30 Minutes").build();


        String currentTimerType = "None";
        if (questManager.isCooldownBased()){
             currentTimerType = ChatColor.GOLD + "Individual Cooldowns";
        }

        if (!questManager.isCooldownBased()){
            currentTimerType = ChatColor.GOLD + "Global Cooldown";
        }

        ItemStack timerType = new ItemBuilder(Material.COMPARATOR)
                .withDisplayName(ChatColor.GOLD + "Cooldown Type")
                .withLore(ChatColor.GREEN + "Current: " + currentTimerType
                        , ChatColor.YELLOW + "LEFT-CLICK: Individual Cooldowns", ChatColor.YELLOW + "RIGHT-CLICK: Global Cooldown").build();

        clickedQuestInv.setItem(1, enableDisable);
        clickedQuestInv.setItem(2, changeReward);
        clickedQuestInv.setItem(3, changeMinTask);
        clickedQuestInv.setItem(4, changeMaxTask);
        clickedQuestInv.setItem(6, changeQuestAmount);
        clickedQuestInv.setItem(7, changeResetTimer);
        clickedQuestInv.setItem(8, timerType);

        player.openInventory(clickedQuestInv);

    }
    public void click(Player player){
        player.playSound(player, Sound.UI_BUTTON_CLICK,0.5F ,1);
    }
}
