package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class InsomniaTotem {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getTotemName());
            List<String> lore = data.getTotemLore();
            lore.add("");
            lore.add("§5⚡ §fДар Фантома при срабатывании:");
            lore.add("§7- Сила II и Скорость II (15 сек)");
            lore.add("§7- Звуковой взрыв вокруг");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика при срабатывании тотема
    public static void applyPower(Player player) {
        // Эффекты для игрока
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 300, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 0));

        // Визуальный эффект и звук
        player.getWorld().spawnParticle(Particle.WITCH, player.getLocation(), 100, 1, 2, 1, 0.1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_DEATH, 1.5f, 0.5f);

        // Отбрасывание врагов
        for (Entity e : player.getNearbyEntities(5, 5, 5)) {
            if (e instanceof LivingEntity target && e != player) {
                target.damage(4.0, player); // Наносит 2 сердца чистого урона
                target.setVelocity(target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.2));
            }
        }
    }
}