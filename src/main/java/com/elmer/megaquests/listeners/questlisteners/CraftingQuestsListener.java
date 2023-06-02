package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CraftingQuestsListener implements Listener {

    private final MegaQuests megaQuests;
    private final QuestManager questManager;

    public CraftingQuestsListener(MegaQuests megaQuests, QuestManager questManager){
        this.megaQuests = megaQuests;
        this.questManager = questManager;
    }

    @EventHandler
    public void onCraft (CraftItemEvent e){
        if (e.getWhoClicked() instanceof Player){
            Player player = ((Player) e.getWhoClicked()).getPlayer();
            Material craftedItem = e.getCurrentItem().getType();

            Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();
            if (questGUI != null){

                Map<Material, Quests> craftedToQuests = new HashMap<>();
                craftedToQuests.put(Material.BEACON, Quests.CRAFTBEACON);
                craftedToQuests.put(Material.NETHERITE_INGOT, Quests.CRAFTNETHERITE);
                craftedToQuests.put(Material.SPYGLASS, Quests.CRAFTSPYGLASS);
                craftedToQuests.put(Material.CONDUIT, Quests.CRAFTCONDUIT);
                craftedToQuests.put(Material.ANVIL, Quests.CRAFTANVIL);

                if (craftedToQuests.containsKey(craftedItem)){
                    for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++){
                        Quests associatedQuest = craftedToQuests.get(craftedItem);
                        if (questGUI.getItem(i).getType().equals(associatedQuest.getItemDisplay())
                                && !questManager.checkCompletedEnabled(player, associatedQuest)){
                            questManager.checkCompletion(player, associatedQuest, 1);
                        }
                    }
                }
                /*for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++){



                    if (craftedItem.getType().equals(Material.BEACON) && questGUI.getItem(i).getType().equals(Quests.CRAFTBEACON.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTBEACON)){
                        questManager.checkCompletion(player, Quests.CRAFTBEACON,1);
                    }
                    if(craftedItem.getType().equals(Material.NETHERITE_INGOT) && questGUI.getItem(i).getType().equals(Quests.CRAFTNETHERITE.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTNETHERITE)){
                        questManager.checkCompletion(player, Quests.CRAFTNETHERITE, 1);
                    }
                    if (craftedItem.getType().equals(Material.SPYGLASS) && questGUI.getItem(i).getType().equals(Quests.CRAFTSPYGLASS.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTSPYGLASS)){
                        questManager.checkCompletion(player, Quests.CRAFTSPYGLASS, 1);
                    }
                    if (craftedItem.getType().equals(Material.CONDUIT) && questGUI.getItem(i).getType().equals(Quests.CRAFTCONDUIT.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTCONDUIT)){
                        questManager.checkCompletion(player, Quests.CRAFTCONDUIT, 1);
                    }
                    if (craftedItem.getType().equals(Material.ANVIL) && questGUI.getItem(i).getType().equals(Quests.CRAFTANVIL.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.CRAFTANVIL)){
                        questManager.checkCompletion(player, Quests.CRAFTANVIL, 1);
                    }
                }*/
            }



        }
    }
}
