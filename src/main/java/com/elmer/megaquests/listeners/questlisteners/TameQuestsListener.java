package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.inventory.Inventory;

public class TameQuestsListener implements Listener {
    MegaQuests megaQuests;

    public TameQuestsListener(MegaQuests megaQuests){
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onTame (EntityTameEvent e){
        QuestManager questManager = megaQuests.getQuestManager();
        Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI();

        if (e.getOwner() instanceof Player && questGUI != null  ){
            Player player = (Player) e.getOwner();
            for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot()  + 1; i++){
                if (e.getEntity() instanceof  Horse && questGUI.getItem(i).getType().equals(Quests.TAMEHORSE.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.TAMEHORSE)){
                    questManager.checkCompletion(player, Quests.TAMEHORSE, 1);
                }
                if (e.getEntity() instanceof Wolf && questGUI.getItem(i).getType().equals(Quests.TAMEWOLF.getItemDisplay()) && !questManager.checkCompletedEnabled(player, Quests.TAMEWOLF)){
                    questManager.checkCompletion(player, Quests.TAMEWOLF, 1);
                }
                //if (e.getEntity() instanceof Cat && questGUI.getItem(i).getType().equals())
            }

        }
    }
}
