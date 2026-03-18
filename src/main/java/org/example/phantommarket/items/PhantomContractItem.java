package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.utils.ColorUtils;
import java.util.List;

public class PhantomContractItem {
    public static ItemStack getItem(PhantomMarket plugin) {
        var config = plugin.getConfig();
        String name = config.getString("items.phantom-contract.name", "&6Контракт Фантома");
        List<String> lore = config.getStringList("items.phantom-contract.lore");

        ItemStack item = new ItemStack(Material.PAPER);
        var meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ColorUtils.translateLegacy(name));
            meta.setLore(lore.stream().map(ColorUtils::translateLegacy).toList());
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "is_phantom_contract"), PersistentDataType.BYTE, (byte) 1);
            item.setItemMeta(meta);
        }
        return item;
    }
}