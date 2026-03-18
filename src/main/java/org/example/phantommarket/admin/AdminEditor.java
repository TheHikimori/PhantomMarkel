package org.example.phantommarket.admin;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.items.*;
import org.example.phantommarket.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class AdminEditor {
    private final PhantomMarket plugin;

    public AdminEditor(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    // --- ИСПРАВЛЕНИЕ: Статический метод для открытия меню из любой точки плагина ---
    public static void open(Player player) {
        // Получаем главный класс плагина, чтобы передать его в конструктор
        PhantomMarket plugin = PhantomMarket.getPlugin(PhantomMarket.class);
        new AdminEditor(plugin).openMainMenu(player);
    }

    public void openMainMenu(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("§5§lПризрачный Рынок"))
                .rows(5)
                .disableAllInteractions()
                .create();

        // 1. Товары за Пыль Пустоты (VoidDust)
        addCustomShopItem(gui, player, 10, PhantomReaper.getItem(plugin), VoidDust.getItem(plugin), 32);
        addCustomShopItem(gui, player, 11, TraitorDagger.getItem(plugin), VoidDust.getItem(plugin), 16);
        addCustomShopItem(gui, player, 19, PhantomMask.getItem(plugin), VoidDust.getItem(plugin), 12);
        addCustomShopItem(gui, player, 20, ShadowCloak.getItem(plugin), VoidDust.getItem(plugin), 20);

        // 2. Товары за Осколки Эфира (Shard)
        addCustomShopItem(gui, player, 12, VoidPickaxe.getItem(plugin), EtherItems.getShard(plugin), 8);
        addCustomShopItem(gui, player, 13, EchoPulse.getItem(plugin), EtherItems.getShard(plugin), 4);
        addCustomShopItem(gui, player, 28, WindSphere.getItem(plugin), EtherItems.getShard(plugin), 2);
        addCustomShopItem(gui, player, 29, DashSpell.getItem(plugin), EtherItems.getShard(plugin), 3);

        // 3. Легендарные товары
        addCustomShopItem(gui, player, 30, SleepAmulet.getItem(plugin), EtherItems.getShard(plugin), 2);
        addCustomShopItem(gui, player, 31, InsomniaTotem.getItem(plugin), EtherItems.getShard(plugin), 5); // Исправлен слот, чтобы не дублировать

        // 4. Обмен валют
        // addCustomShopItem(gui, player, 31, VoidDust.getItem(plugin), new ItemStack(Material.EMERALD), 16);

        // 5. Контракты
        addCustomShopItem(gui, player, 32, PhantomContractItem.getItem(plugin), EtherItems.getShard(plugin), 50);
        addCustomShopItem(gui, player, 33, WardenContract.getItem(plugin), EtherItems.getShard(plugin), 50);

        // Заполнение пустот
        gui.getFiller().fill(new GuiItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 0.5f);
        gui.open(player);
    }

    private void addCustomShopItem(Gui gui, Player player, int slot, ItemStack product, ItemStack currency, int price) {
        if (product == null || currency == null) return;

        ItemStack displayItem = product.clone();
        ItemMeta meta = displayItem.getItemMeta();

        if (meta != null) {
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            // Безопасное получение имени валюты
            String currencyName = (currency.hasItemMeta() && currency.getItemMeta().hasDisplayName())
                    ? currency.getItemMeta().getDisplayName()
                    : currency.getType().name();

            lore.add(" ");
            lore.add(ColorUtils.translateLegacy("&7-----------------------"));
            lore.add(ColorUtils.translateLegacy("&eЦена: &f" + price + "x " + currencyName));
            lore.add(ColorUtils.translateLegacy("&7-----------------------"));
            meta.setLore(lore);
            displayItem.setItemMeta(meta);
        }

        gui.setItem(slot, new GuiItem(displayItem, event -> {
            buyItem(player, product, currency, price);
        }));
    }

    private void buyItem(Player player, ItemStack product, ItemStack currency, int price) {
        int count = 0;
        // Считаем валюту
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem != null && invItem.isSimilar(currency)) {
                count += invItem.getAmount();
            }
        }

        if (count >= price) {
            int toRemove = price;
            ItemStack[] contents = player.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack invItem = contents[i];
                if (invItem != null && invItem.isSimilar(currency)) {
                    int amount = invItem.getAmount();
                    if (amount > toRemove) {
                        invItem.setAmount(amount - toRemove);
                        toRemove = 0;
                        break;
                    } else {
                        toRemove -= amount;
                        player.getInventory().setItem(i, null);
                    }
                }
                if (toRemove <= 0) break;
            }
            player.getInventory().addItem(product.clone());
            player.sendMessage(ColorUtils.translateLegacy("&a[✔] Вы успешно приобрели предмет!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1.2f);
        } else {
            player.sendMessage(ColorUtils.translateLegacy("&c[✘] У вас недостаточно ресурсов!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
        }
    }
}