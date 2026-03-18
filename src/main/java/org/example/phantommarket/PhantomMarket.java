package org.example.phantommarket;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.phantommarket.admin.BossEditor;
import org.example.phantommarket.commands.PhantomCommand;
import org.example.phantommarket.items.ItemManager;
import org.example.phantommarket.listeners.BossListener;
import org.example.phantommarket.listeners.MarketListener;
import org.example.phantommarket.utils.ColorUtils;

public final class PhantomMarket extends JavaPlugin {

    @Override
    public void onEnable() {
        // 1. Загрузка конфига
        saveDefaultConfig();

        // 2. Инициализация редакторов и предметов
        BossEditor.init(this);
        ItemManager.init(this);

        // 3. Регистрация команд (ИСПРАВЛЕНО)
        if (getCommand("phantom") != null) {
            PhantomCommand cmd = new PhantomCommand(this);
            getCommand("phantom").setExecutor(cmd);
            getCommand("phantom").setTabCompleter(cmd); // Чтобы работали подсказки /pm shop
        }

        // 4. События
        registerEvents();

        getLogger().info(ColorUtils.translateLegacy("&5[PhantomMarket] &aПлагин запущен! Житель и боссы готовы."));
    }

    private void registerEvents() {
        var pm = getServer().getPluginManager();
        pm.registerEvents(new BossListener(this), this);
        pm.registerEvents(new MarketListener(this), this);
        // Слушатель для контрактов и кликов по жителю
        pm.registerEvents(new org.example.phantommarket.listeners.PhantomContract(this), this);
    }

    public void reloadPlugin() {
        reloadConfig();
        BossEditor.loadLoot();
        ItemManager.init(this);
        getLogger().info("§a[PhantomMarket] Перезагружено!");
    }
}