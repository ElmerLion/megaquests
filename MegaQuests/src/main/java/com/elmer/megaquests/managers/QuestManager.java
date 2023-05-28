package com.elmer.megaquests.managers;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.Quests;
import net.ess3.api.MaxMoneyException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class QuestManager {


    MegaQuests megaQuests;
    private int questGUIAmount = 5;

    public QuestManager(MegaQuests megaQuests){ this.megaQuests = megaQuests; }
    public void checkCompletion(Player player, Quests quests, int progress){
        quests.addProgress(player.getUniqueId(),progress);
        if (quests.getProgress(player.getUniqueId()) == quests.getTaskAmount()){
            BigDecimal rewardAmount = new BigDecimal(quests.getReward());

            try {
                megaQuests.getEssentials().getUser(player).giveMoney(rewardAmount);
            } catch (MaxMoneyException ex) {
                throw new RuntimeException(ex);
            }

            player.sendMessage(ChatColor.GREEN + "You completed " + quests.getDisplay() + ChatColor.GREEN + " and got $" + quests.getReward());
            quests.setCompleted(true);
        }


    }

    public int getQuestGUIAmount() {
        return questGUIAmount;
    }
}
