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
import org.bukkit.util.Vector;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class EchoPulse {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getEchoPulseName());
            List<String> lore = data.getEchoPulseLore();
            lore.add("");
            lore.add("§7[ПКМ] §bЗвуковая волна");
            lore.add("§7Перезарядка: §f10 сек.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика способности: Расталкивание всех вокруг
    public static void execute(Player player) {
        player.getWorld().spawnParticle(Particle.SONIC_BOOM, player.getLocation().add(0, 1, 0), 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1.2f, 0.5f);

        for (Entity entity : player.getNearbyEntities(6, 4, 6)) {
            if (entity instanceof LivingEntity target && entity != player) {
                // Расталкивание
                Vector push = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.5).setY(0.5);
                target.setVelocity(push);

                // Эффект оглушения (медлительность)
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 2));
            }
        }
        player.sendMessage("§b🌀 Эхо-импульс выпущен!");
    }
}