package com.elmer.megaquests.listeners.questlisteners;

import com.elmer.megaquests.MegaQuests;
import com.elmer.megaquests.enums.Quests;
import com.elmer.megaquests.managers.QuestManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class EntityKillQuestsListener implements Listener {

    private final QuestManager questManager;
    private final MegaQuests megaQuests;
    public EntityKillQuestsListener(MegaQuests megaQuests, QuestManager questManager){
        this.questManager = questManager;
        this.megaQuests = megaQuests;
    }

    @EventHandler
    public void onEntityDeath (EntityDeathEvent e){
        if (e.getEntity().getKiller() != null){
            Player player = (Player) e.getEntity().getKiller();
            Inventory questGUI = megaQuests.getQuestGUICommand().getQuestGUI(player);

            if (questGUI != null){

                Map<EntityType, Quests> entityToQuest = new HashMap<>();
                entityToQuest.put(EntityType.SKELETON, Quests.KILLSKELETON);
                entityToQuest.put(EntityType.ZOMBIE, Quests.KILLZOMBIE);
                entityToQuest.put(EntityType.PLAYER, Quests.KILLPLAYER);
                entityToQuest.put(EntityType.CREEPER, Quests.KILLCREEPER);
                entityToQuest.put(EntityType.SPIDER, Quests.KILLSPIDER);
                entityToQuest.put(EntityType.ENDERMAN, Quests.KILLENDERMAN);
                entityToQuest.put(EntityType.WITCH, Quests.KILLWITCH);
                entityToQuest.put(EntityType.CAVE_SPIDER, Quests.KILLCAVESPIDER);
                entityToQuest.put(EntityType.BLAZE, Quests.KILLBLAZE);
                entityToQuest.put(EntityType.GHAST, Quests.KILLGHAST);
                entityToQuest.put(EntityType.MAGMA_CUBE, Quests.KILLMAGMACUBE);
                entityToQuest.put(EntityType.DROWNED, Quests.KILLDROWNED);
                entityToQuest.put(EntityType.PHANTOM, Quests.KILLPHANTOM);
                entityToQuest.put(EntityType.HUSK, Quests.KILLHUSK);
                entityToQuest.put(EntityType.STRAY, Quests.KILLSTRAY);
                entityToQuest.put(EntityType.WITHER_SKELETON, Quests.KILLWITHERSKELETON);
                entityToQuest.put(EntityType.EVOKER, Quests.KILLEVOKER);
                entityToQuest.put(EntityType.VEX, Quests.KILLVEX);
                entityToQuest.put(EntityType.PILLAGER, Quests.KILLPILLAGER);
                entityToQuest.put(EntityType.RAVAGER, Quests.KILLRAVAGER);
                entityToQuest.put(EntityType.VINDICATOR, Quests.KILLVINDICATOR);
                entityToQuest.put(EntityType.ENDERMITE, Quests.KILLENDERMITE);
                entityToQuest.put(EntityType.GUARDIAN, Quests.KILLGUARDIAN);
                entityToQuest.put(EntityType.ELDER_GUARDIAN, Quests.KILLELDERGUARDIAN);
                entityToQuest.put(EntityType.SHULKER, Quests.KILLSHULKER);
                entityToQuest.put(EntityType.SLIME, Quests.KILLSLIME);
                entityToQuest.put(EntityType.SILVERFISH, Quests.KILLSILVERFISH);
                entityToQuest.put(EntityType.ENDER_DRAGON, Quests.KILLENDERDRAGON);
                entityToQuest.put(EntityType.WITHER, Quests.KILLWITHER);
                entityToQuest.put(EntityType.CHICKEN, Quests.KILL_CHICKEN);
                entityToQuest.put(EntityType.COW, Quests.KILL_COW);
                entityToQuest.put(EntityType.SHEEP, Quests.KILL_SHEEP);
                entityToQuest.put(EntityType.PIG, Quests.KILL_PIG);
                entityToQuest.put(EntityType.HORSE, Quests.KILL_HORSE);
                entityToQuest.put(EntityType.DONKEY, Quests.KILL_DONKEY);
                entityToQuest.put(EntityType.RABBIT, Quests.KILL_RABBIT);
                entityToQuest.put(EntityType.POLAR_BEAR, Quests.KILL_POLAR_BEAR);
                entityToQuest.put(EntityType.LLAMA, Quests.KILL_LLAMA);
                entityToQuest.put(EntityType.MUSHROOM_COW, Quests.KILL_MUSHROOM_COW);
                entityToQuest.put(EntityType.BEE, Quests.KILL_BEE);
                entityToQuest.put(EntityType.DOLPHIN, Quests.KILL_DOLPHIN);
                entityToQuest.put(EntityType.SNOWMAN, Quests.KILL_SNOW_GOLEM);
                entityToQuest.put(EntityType.IRON_GOLEM, Quests.KILL_IRON_GOLEM);


                for (int i = questManager.getStartingSlot(); i < questManager.getEndingSlot() + 1; i++){
                    if(entityToQuest.containsKey(e.getEntityType())) {
                        Quests associatedQuest = entityToQuest.get(e.getEntityType());
                        if (questGUI.getItem(i).getType().equals(associatedQuest.getItemDisplay()) && !questManager.checkCompletedEnabled(player, associatedQuest)){
                            questManager.checkCompletion(player, associatedQuest, 1);
                        }
                    }
                }






            }

        }
    }
}
