package org.example.phantommarket.boss;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface PhantomBossBase {
    LivingEntity spawnLegendary(Location loc);
    LivingEntity spawnNormal(Location loc);
}