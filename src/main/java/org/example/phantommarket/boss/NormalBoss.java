package org.example.phantommarket.boss;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Warden;
import org.bukkit.metadata.FixedMetadataValue;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.utils.ColorUtils;

public class NormalBoss implements PhantomBossBase {
    private final PhantomMarket plugin;

    public NormalBoss(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    // Реализация для команды warden-legendary (хоть класс и Normal, логика внутри для варденов)
    @Override
    public LivingEntity spawnLegendary(Location loc) {
        return createWarden(loc, "&d&lЛЕГЕНДАРНЫЙ Варден-Босс", "legendary", 1000.0, 20.0);
    }

    // Реализация для команды warden-normal
    @Override
    public LivingEntity spawnNormal(Location loc) {
        return createWarden(loc, "&5Варден-Рекрут", "normal", 500.0, 10.0);
    }

    // Вспомогательный метод
    private Warden createWarden(Location loc, String name, String type, double hp, double armor) {
        Warden boss = (Warden) loc.getWorld().spawnEntity(loc, EntityType.WARDEN);
        boss.setCustomName(ColorUtils.translateLegacy(name));
        boss.setCustomNameVisible(true);
        boss.setMetadata("boss_type", new FixedMetadataValue(plugin, type));

        var healthAttr = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthAttr != null) {
            healthAttr.setBaseValue(hp);
            boss.setHealth(hp);
        }

        var armorAttr = boss.getAttribute(Attribute.GENERIC_ARMOR);
        if (armorAttr != null) {
            armorAttr.setBaseValue(armor);
        }

        return boss;
    }
}