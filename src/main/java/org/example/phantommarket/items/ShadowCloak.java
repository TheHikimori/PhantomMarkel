package org.example.phantommarket.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class ShadowCloak {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getShadowCloakName());
            List<String> lore = data.getShadowCloakLore();
            lore.add("");
            lore.add("§8[ПКМ] §fСлиться с тенями");
            lore.add("§7- Невидимость (10 сек)");
            lore.add("§7- Замедление II (10 сек)");
            lore.add("§7Перезарядка: §f30 сек.");
            meta.setLore(lore);
            meta.setColor(Color.BLACK);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика активации невидимости
    public static void activate(Player player) {
        // Эффекты
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 200, 1, false, false));

        // Визуализация и звук
        player.getWorld().spawnParticle(Particle.LARGE_SMOKE, player.getLocation().add(0, 1, 0), 30, 0.3, 0.5, 0.3, 0.05);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 0.5f);

        player.sendMessage("§8[✦] Вы растворились в тенях...");
    }
}