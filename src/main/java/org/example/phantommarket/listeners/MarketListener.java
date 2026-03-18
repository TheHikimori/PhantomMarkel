package org.example.phantommarket.listeners;

import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.admin.AdminEditor;
import org.example.phantommarket.items.ItemManager;

public class MarketListener implements Listener {
    private final PhantomMarket plugin;

    public MarketListener(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    // 1. Открытие магазина и защита от обычного интерфейса жителя
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Villager v && v.getCustomName() != null && v.getCustomName().contains("Торговец")) {
            e.setCancelled(true);
            // Используем статический метод open, как в твоем AdminEditor
            AdminEditor.open(e.getPlayer());
        }
    }

    // 2. Защита торговца от ударов (чтобы его нельзя было бить)
    @EventHandler
    public void onNPCDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity().getCustomName() != null && e.getEntity().getCustomName().contains("Торговец")) {
            e.setCancelled(true);
        }
    }

    // 3. Дроп с фантомов (Пыль и Осколки)
    @EventHandler
    public void onPhantomDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Phantom) {
            // Выпадение пыли с шансом 30%
            if (Math.random() < 0.30) {
                ItemStack dust = ItemManager.getItem("dust");
                if (dust != null) e.getDrops().add(dust);
            }

            // Выпадение осколка с шансом 1%
            if (Math.random() < 0.01) {
                ItemStack shard = ItemManager.getItem("shard");
                if (shard != null) e.getDrops().add(shard);
            }
        }
    }
}