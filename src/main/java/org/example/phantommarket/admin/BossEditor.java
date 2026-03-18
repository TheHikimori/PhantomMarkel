package org.example.phantommarket.admin;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.utils.ColorUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossEditor {
    private static File lootFile;
    private static FileConfiguration lootConfig;
    private static final List<LootEntry> lootList = new ArrayList<>();

    private static class LootEntry {
        ItemStack item;
        int chance;
        LootEntry(ItemStack item, int chance) { this.item = item; this.chance = chance; }
    }

    public static void init(PhantomMarket plugin) {
        lootFile = new File(plugin.getDataFolder(), "boss_loot.yml");
        if (!lootFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try { lootFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        lootConfig = YamlConfiguration.loadConfiguration(lootFile);
        loadLoot();
    }

    public static void addItem(ItemStack item, int chance) {
        lootList.add(new LootEntry(item.clone(), chance));
        saveLoot();
    }

    public static void open(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("§8Настройка (ПКМ-Шанс, ЛКМ-Удалить)"))
                .rows(3)
                .disableAllInteractions()
                .create();

        for (int i = 0; i < lootList.size(); i++) {
            LootEntry entry = lootList.get(i);
            int index = i;

            ItemStack displayItem = entry.item.clone();
            updateItemLore(displayItem, entry.chance);

            gui.setItem(i, new GuiItem(displayItem, event -> {
                if (event.isRightClick()) {
                    openChanceEditor(player, index);
                } else if (event.isLeftClick()) {
                    lootList.remove(index);
                    saveLoot();
                    open(player);
                }
            }));
        }
        gui.open(player);
    }

    private static void openChanceEditor(Player player, int index) {
        LootEntry entry = lootList.get(index);
        Gui gui = Gui.gui().title(Component.text("§0Шанс: §d" + entry.chance + "%")).rows(1).disableAllInteractions().create();

        gui.setItem(2, createBtn(Material.RED_STAINED_GLASS_PANE, "§c-10%").asGuiItem(e -> update(player, index, -10)));
        gui.setItem(3, createBtn(Material.PINK_STAINED_GLASS_PANE, "§c-1%").asGuiItem(e -> update(player, index, -1)));
        gui.setItem(4, createBtn(Material.PAPER, "§fШанс: §d" + entry.chance + "%").asGuiItem());
        gui.setItem(5, createBtn(Material.LIME_STAINED_GLASS_PANE, "§a+1%").asGuiItem(e -> update(player, index, 1)));
        gui.setItem(6, createBtn(Material.GREEN_STAINED_GLASS_PANE, "§a+10%").asGuiItem(e -> update(player, index, 10)));
        gui.setItem(8, createBtn(Material.BARRIER, "§7Назад").asGuiItem(e -> open(player)));

        gui.open(player);
    }

    private static void update(Player p, int index, int delta) {
        LootEntry entry = lootList.get(index);
        entry.chance = Math.min(100, Math.max(1, entry.chance + delta));
        saveLoot();
        openChanceEditor(p, index);
    }

    private static void updateItemLore(ItemStack item, int chance) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add(" ");
        lore.add(ColorUtils.translateLegacy("&dШанс выпадения: &f" + chance + "%"));
        lore.add(ColorUtils.translateLegacy("&8[ЛКМ] Удалить предмет"));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private static dev.triumphteam.gui.builder.item.ItemBuilder createBtn(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) { meta.setDisplayName(name); item.setItemMeta(meta); }
        return dev.triumphteam.gui.builder.item.ItemBuilder.from(item);
    }

    public static void saveLoot() {
        lootConfig.set("drops", null);
        for (int i = 0; i < lootList.size(); i++) {
            lootConfig.set("drops." + i + ".item", lootList.get(i).item);
            lootConfig.set("drops." + i + ".chance", lootList.get(i).chance);
        }
        try { lootConfig.save(lootFile); } catch (IOException e) { e.printStackTrace(); }
    }

    public static void loadLoot() {
        lootList.clear();
        if (lootConfig.getConfigurationSection("drops") == null) return;
        for (String key : lootConfig.getConfigurationSection("drops").getKeys(false)) {
            ItemStack item = lootConfig.getItemStack("drops." + key + ".item");
            int chance = lootConfig.getInt("drops." + key + ".chance", 50);
            if (item != null) lootList.add(new LootEntry(item, chance));
        }
    }

    /**
     * Возвращает список выпавших предметов БЕЗ технических строк в Lore
     */
    public static List<ItemStack> getLootList() {
        List<ItemStack> drops = new ArrayList<>();
        Random random = new Random();
        for (LootEntry entry : lootList) {
            if (random.nextInt(100) < entry.chance) {
                ItemStack cleanItem = entry.item.clone();
                cleanMeta(cleanItem);
                drops.add(cleanItem);
            }
        }
        return drops;
    }

    private static void cleanMeta(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) return;
        List<String> lore = meta.getLore();

        // Удаляем последние 3 строки (пустая строка, шанс, инструкция)
        if (lore.size() >= 3) {
            lore.remove(lore.size() - 1);
            lore.remove(lore.size() - 1);
            lore.remove(lore.size() - 1);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}