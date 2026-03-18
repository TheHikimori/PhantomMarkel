package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;

public class EtherItems {

    // Добавляем этот метод специально для AdminEditor
    public static ItemStack getEther(PhantomMarket plugin) {
        return getShard(plugin);
    }

    public static ItemStack getShard(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getEtherItemsName());
            meta.setLore(data.getEtherItemsLore());
            item.setItemMeta(meta);
        }
        return item;
    }
}