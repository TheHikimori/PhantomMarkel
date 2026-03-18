package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class TraitorDagger {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getTraitorDaggerName());
            List<String> lore = data.getTraitorDaggerLore();
            lore.add("");
            lore.add("§4☠ §fОсобенность: §cУдар в спину");
            lore.add("§7- Урон x2.5 при атаке сзади");
            lore.add("§7- Накладывает кровотечение");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика проверки удара в спину
    public static double processBackstab(Player attacker, LivingEntity victim, double damage) {
        // Вычисляем угол между направлением взгляда атакующего и жертвы
        // Если они смотрят примерно в одну сторону (угол < 60 градусов), это удар в спину
        double angle = attacker.getLocation().getDirection().dot(victim.getLocation().getDirection());

        if (angle > 0.7) { // 0.7-1.0 означает, что они смотрят в одном направлении
            // Эффекты удара
            attacker.playSound(attacker.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.2f, 0.5f);
            victim.getWorld().spawnParticle(Particle.BLOCK, victim.getLocation().add(0, 1, 0), 30, 0.2, 0.2, 0.2, Material.REDSTONE_BLOCK.createBlockData());

            // Накладываем иссушение
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1));

            attacker.sendMessage("§4§l☠ §cУДАР В СПИНУ!");
            return damage * 2.5; // Увеличиваем урон
        }

        return damage;
    }
}