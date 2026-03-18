package org.example.phantommarket.listeners; // Можно перенести в listeners

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.boss.LegendaryBoss;
import org.example.phantommarket.boss.NormalBoss;
import org.example.phantommarket.utils.ColorUtils;

public class PhantomContract implements Listener {
    private final PhantomMarket plugin;

    public PhantomContract(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = e.getItem();
        if (item == null || !item.hasItemMeta()) return;

        var container = item.getItemMeta().getPersistentDataContainer();

        // Проверка на контракт Вардена (NormalBoss)
        if (container.has(new NamespacedKey(plugin, "is_warden_contract"), PersistentDataType.BYTE)) {
            e.setCancelled(true);
            item.setAmount(item.getAmount() - 1);
            new NormalBoss(plugin).spawnLegendary(e.getPlayer().getLocation());
            e.getPlayer().sendMessage(ColorUtils.translateLegacy("&a[✔] Контракт исполнен! Варден-Лорд явился."));
            return;
        }

        // Проверка на контракт Фантома (LegendaryBoss)
        if (container.has(new NamespacedKey(plugin, "is_phantom_contract"), PersistentDataType.BYTE)) {
            e.setCancelled(true);
            item.setAmount(item.getAmount() - 1);
            new LegendaryBoss(plugin).spawnLegendary(e.getPlayer().getLocation());
            e.getPlayer().sendMessage(ColorUtils.translateLegacy("&d[✔] Контракт исполнен! Лорд Фантомов в небе."));
        }
    }
}