package com.elmer.megaquests;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder withDisplayName(String displayName) {
        ItemMeta itemMeta = item.getItemMeta();
        if (Objects.nonNull(itemMeta)) {
            itemMeta.setDisplayName(displayName);
            item.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return item;
    }
}
