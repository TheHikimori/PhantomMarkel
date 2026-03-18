package org.example.phantommarket.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class VoidElixir {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getVoidElixirName());
            List<String> lore = data.getVoidElixirLore();
            lore.add("");
            lore.add("§5✧ §fСостояние призрака (5 сек):");
            lore.add("§7- Неуязвимость к снарядам");
            lore.add("§7- Огнестойкость и Скорость III");
            meta.setLore(lore);
            meta.setColor(Color.PURPLE);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика выпивания эликсира
    public static void applyEffect(Player player) {
        // Накладываем мощные защитные эффекты
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 4)); // Почти бессмертие

        // Эффекты Пустоты
        player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 50, 0.5, 1, 0.5, 0.05);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1f, 0.5f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_AMBIENT, 1f, 1.5f);

        player.sendMessage("§d[✦] Вы чувствуете, как ваше тело становится бесплотным...");
    }
}