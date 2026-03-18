package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class VoidPickaxe {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getVoidPickaxeName());
            List<String> lore = data.getVoidPickaxeLore();
            lore.add("");
            lore.add("§5✧ §fОсобенность: §dПрилив Пустоты");
            lore.add("§7- Шанс мгновенной переплавки руды");
            lore.add("§7- Шанс получить Спешку II на 5 сек");
            meta.setLore(lore);
            meta.addEnchant(Enchantment.EFFICIENCY, 6, true);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика при срабатывании эффекта
    public static void applyVoidPower(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 100, 1));
        player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(0, 1, 0), 20, 0.5, 0.5, 0.5, 0.1);
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.8f, 1.5f);
    }
}