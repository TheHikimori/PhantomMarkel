package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class PhantomHead {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        // Используем череп скелета как основу
        ItemStack item = new ItemStack(Material.SKELETON_SKULL);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getPhantomHeadName());
            List<String> lore = data.getPhantomHeadLore();
            lore.add("");
            lore.add("§8[Экипировка] §fДарует Взор Пустоты");
            lore.add("§7- Ночное зрение");
            lore.add("§7- Иммунитет к атакам фантомов");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика пассивного эффекта
    public static void applyPassive(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0, false, false));
    }
}