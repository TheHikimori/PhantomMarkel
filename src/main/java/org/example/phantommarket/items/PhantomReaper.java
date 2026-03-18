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

public class PhantomReaper {

    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(data.getPhantomReaperName());
            List<String> lore = data.getPhantomReaperLore();
            lore.add("");
            lore.add("§4☠ §fСпособность: §6Жатва");
            lore.add("§7- Шанс наложить Иссушение II");
            lore.add("§7- Восстанавливает сытость при убийстве");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    // Логика удара косой
    public static void applyEffect(Player attacker, LivingEntity victim) {
        // Эффект иссушения с шансом 25%
        if (Math.random() < 0.25) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1));
            attacker.playSound(attacker.getLocation(), Sound.ENTITY_VEX_CHARGE, 1f, 0.5f);
            victim.getWorld().spawnParticle(Particle.SMOKE, victim.getLocation().add(0, 1, 0), 20, 0.2, 0.5, 0.2, 0.02);
        }
    }
}