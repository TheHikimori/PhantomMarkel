package org.example.phantommarket.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.utils.ColorUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ItemData {

    private final PhantomMarket plugin;

    public ItemData(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    // Сокращенный метод для получения НАЗВАНИЯ (Строки)
    private String s(String path, String def) {
        String raw = plugin.getConfig().getString("items." + path + ".name", def);
        return ColorUtils.translateLegacy(raw);
    }

    // Сокращенный метод для получения ЛОРА (Списка)
    private List<String> l(String path) {
        List<String> raw = plugin.getConfig().getStringList("items." + path + ".lore");
        return raw.stream()
                .map(ColorUtils::translateLegacy)
                .collect(Collectors.toList());
    }

    // --- Методы для ВСЕХ предметов из папки items ---

    public String getDashName() { return s("dash-spell", "&bЗаклинание Рывка"); }
    public List<String> getDashLore() { return l("dash-spell"); }

    public String getEchoArrowName() { return s("echo-arrow", "&3Эхо-Стрела"); }
    public List<String> getEchoArrowLore() { return l("echo-arrow"); }

    public String getEchoPulseName() { return s("echo-pulse", "&9Эхо-Импульс"); }
    public List<String> getEchoPulseLore() { return l("echo-pulse"); }

    public String getEtherItemsName() { return s("ether-items", "&dЭфирные Предметы"); }
    public List<String> getEtherItemsLore() { return l("ether-items"); }

    public String getTotemName() { return s("totem", "&5Тотем Бессонницы"); }
    public List<String> getTotemLore() { return l("totem"); }

    public String getPhantomContractName() { return s("phantom-contract", "&6Контракт Фантома"); }
    public List<String> getPhantomContractLore() { return l("phantom-contract"); }

    public String getPhantomHeadName() { return s("phantom-head", "&dГолова Фантома"); }
    public List<String> getPhantomHeadLore() { return l("phantom-head"); }

    public String getPhantomMaskName() { return s("phantom-mask", "&8Маска Фантома"); }
    public List<String> getPhantomMaskLore() { return l("phantom-mask"); }

    public String getPhantomReaperName() { return s("phantom-reaper", "&cЖнец Фантомов"); }
    public List<String> getPhantomReaperLore() { return l("phantom-reaper"); }

    public String getShadowCloakName() { return s("shadow-cloak", "&0Плащ Теней"); }
    public List<String> getShadowCloakLore() { return l("shadow-cloak"); }

    public String getSleepAmuletName() { return s("amulet", "&eАмулет Сна"); }
    public List<String> getSleepAmuletLore() { return l("amulet"); }

    public String getTraitorDaggerName() { return s("traitor-dagger", "&4Кинжал Предателя"); }
    public List<String> getTraitorDaggerLore() { return l("traitor-dagger"); }

    public String getDustName() { return s("dust", "&5Пыль Пустоты"); }
    public List<String> getDustLore() { return l("dust"); }

    public String getVoidElixirName() { return s("void-elixir", "&dЭликсир Пустоты"); }
    public List<String> getVoidElixirLore() { return l("void-elixir"); }

    public String getVoidPickaxeName() { return s("void-pickaxe", "&5Кирка Пустоты"); }
    public List<String> getVoidPickaxeLore() { return l("void-pickaxe"); }

    public String getWardenContractName() { return s("warden-contract", "&1Контракт Вардена"); }
    public List<String> getWardenContractLore() { return l("warden-contract"); }

    public String getWindSphereName() { return s("wind-sphere", "&fСфера Ветра"); }
    public List<String> getWindSphereLore() { return l("wind-sphere"); }
}