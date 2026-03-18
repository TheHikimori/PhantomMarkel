package org.example.phantommarket.listeners;

import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.admin.BossEditor;
import org.example.phantommarket.items.ItemManager;

public class BossListener implements Listener {
    private final PhantomMarket plugin;

    public BossListener(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        // Проверяем, что умер именно Варден
        if (!(event.getEntity() instanceof Warden warden)) return;

        // Проверяем наличие нашей метки boss_type
        if (warden.hasMetadata("boss_type")) {
            String type = warden.getMetadata("boss_type").get(0).asString();

            // Очищаем стандартный дроп Майнкрафта
            event.getDrops().clear();

            // 1. Логика для ЛЕГЕНДАРНОГО босса
            if (type.equals("legendary")) {
                // Выдаем голову напрямую из ItemManager (это исключает Config Null)
                ItemStack head = ItemManager.getItem("head");
                if (head != null) {
                    event.getDrops().add(head);
                }
            }

            // 2. Общий дроп для всех боссов из BossEditor (настроенный тобой через меню)
            // Убедись, что в BossEditor метод getLootList() публичный и статический
            for (ItemStack item : BossEditor.getLootList()) {
                if (item != null) {
                    event.getDrops().add(item);
                }
            }
        }
    }
}