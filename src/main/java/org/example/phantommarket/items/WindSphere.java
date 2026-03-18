package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class WindSphere {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.SNOWBALL);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getWindSphereName());
            List<String> lore = data.getWindSphereLore();
            lore.add("");
            lore.add("§b❂ §fОсобенность: §fПорыв Ветра");
            lore.add("§7- Мощное отбрасывание при попадании");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Логика взрыва ветра
    public static void explode(Entity hitEntity) {
        hitEntity.getWorld().spawnParticle(Particle.CLOUD, hitEntity.getLocation(), 40, 1, 1, 1, 0.1);
        hitEntity.getWorld().playSound(hitEntity.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 1.5f, 0.5f);
        hitEntity.getWorld().playSound(hitEntity.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0f, 0.5f);

        // Расталкиваем всех в радиусе 4 блоков
        for (Entity nearby : hitEntity.getNearbyEntities(4, 3, 4)) {
            if (nearby instanceof LivingEntity target) {
                Vector push = target.getLocation().toVector().subtract(hitEntity.getLocation().toVector()).normalize().multiply(1.8).setY(0.6);
                target.setVelocity(push);
            }
        }
    }
}