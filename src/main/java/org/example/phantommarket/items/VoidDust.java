package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;

public class VoidDust { // Меняй имя класса
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.GOLD_NUGGET); // Меняй материал
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getDustName()); // Меняй метод получения имени
            meta.setLore(data.getDustLore()); // Меняй метод получения лора
            item.setItemMeta(meta);
        }
        return item;
    }
}