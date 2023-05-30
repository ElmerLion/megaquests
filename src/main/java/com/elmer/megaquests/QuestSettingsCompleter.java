package com.elmer.megaquests;

import com.elmer.megaquests.enums.Quests;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestSettingsCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 1){
            List<String> categories = new ArrayList<>();

            for (Quests quest : Quests.values()){
                if (!categories.contains(quest.getCategory())){
                    categories.add(quest.getCategory());
                }
            }

            return StringUtil.copyPartialMatches(args[0], categories, new ArrayList<>());
        }


        return null;
    }
}
