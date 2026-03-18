package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class PhantomMask {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.SKELETON_SKULL);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getPhantomMaskName());
            List<String> lore = data.getPhantomMaskLore();
            lore.add("");
            lore.add("§8[Экипировка] §fСущность ловца:");
            lore.add("§7- Грация дельфина (в воде)");
            lore.add("§7- Сопротивление урону от падения");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Метод для наложения эффектов
    public static void applyPassive(Player player) {
        // Даем грацию дельфина для быстрого плавания
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 0, false, false));
        // Даем сопротивление урону (небольшое)
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 0, false, false));
    }
}