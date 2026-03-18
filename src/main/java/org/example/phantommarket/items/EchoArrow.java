package org.example.phantommarket.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class EchoArrow {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(data.getEchoArrowName());
            meta.setColor(Color.fromRGB(100, 200, 255)); // Светло-голубой цвет стрелы
            List<String> lore = data.getEchoArrowLore();
            lore.add("");
            lore.add("§7Перезарядка: §f3 сек.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика эха: подсвечивает врагов в радиусе 5 блоков от места попадания
    public static void playEchoEffect(Entity hitEntity) {
        hitEntity.getWorld().playSound(hitEntity.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1.0f, 1.8f);

        for (Entity nearby : hitEntity.getNearbyEntities(5, 5, 5)) {
            if (nearby instanceof LivingEntity living && nearby != hitEntity) {
                living.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0)); // Свечение на 5 сек
            }
        }
    }
}