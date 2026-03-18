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

public class SleepAmulet {

    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(data.getSleepAmuletName());
            List<String> lore = data.getSleepAmuletLore();
            lore.add("");
            lore.add("§b✨ §fСпособность: §3Магический сон");
            lore.add("§7- Ослепляет и сковывает врагов (4 сек)");
            lore.add("§7Перезарядка: §f20 сек.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    // Логика способности: Массовое усыпление
    public static void execute(Player player) {
        // Эффекты в мире
        player.getWorld().spawnParticle(Particle.SCULK_CHARGE_POP, player.getLocation().add(0, 1, 0), 50, 2, 1, 2, 0.1);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.5f, 0.1f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.5f);

        // Накладываем эффекты на врагов в радиусе 5 блоков
        for (Entity entity : player.getNearbyEntities(5, 3, 5)) {
            if (entity instanceof LivingEntity target && entity != player) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 4)); // Почти полная неподвижность

                // Визуальные частицы над головой жертвы
                target.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, target.getEyeLocation(), 10, 0.3, 0.3, 0.3, 0.05);
            }
        }
        player.sendMessage("§b[✦] Вы погрузили окружающих в глубокий сон...");
    }
}