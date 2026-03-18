package org.example.phantommarket.boss;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.metadata.FixedMetadataValue;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.utils.ColorUtils;

public class LegendaryBoss implements PhantomBossBase {
    private final PhantomMarket plugin;

    public LegendaryBoss(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    // Реализация для команды phantom-legendary
    @Override
    public LivingEntity spawnLegendary(Location loc) {
        Phantom boss = (Phantom) loc.getWorld().spawnEntity(loc, EntityType.PHANTOM);
        boss.setCustomName(ColorUtils.translateLegacy("&d&lЛЕГЕНДАРНЫЙ Фантом"));
        boss.setCustomNameVisible(true);
        boss.setSize(12); // Большой размер
        boss.setMetadata("boss_type", new FixedMetadataValue(plugin, "legendary"));

        var healthAttr = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthAttr != null) {
            healthAttr.setBaseValue(400.0);
            boss.setHealth(400.0);
        }
        return boss;
    }

    // Реализация для команды phantom-normal
    @Override
    public LivingEntity spawnNormal(Location loc) {
        Phantom boss = (Phantom) loc.getWorld().spawnEntity(loc, EntityType.PHANTOM);
        boss.setCustomName(ColorUtils.translateLegacy("&7Обычный Фантом"));
        boss.setCustomNameVisible(true);
        boss.setSize(5);
        boss.setMetadata("boss_type", new FixedMetadataValue(plugin, "regular"));

        var healthAttr = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthAttr != null) {
            healthAttr.setBaseValue(100.0);
            boss.setHealth(100.0);
        }
        return boss;
    }
}