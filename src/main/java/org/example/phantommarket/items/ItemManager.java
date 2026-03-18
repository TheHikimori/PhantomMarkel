package org.example.phantommarket.items;

import org.bukkit.inventory.ItemStack;
import org.example.phantommarket.PhantomMarket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemManager {
    private static final Map<String, ItemStack> registry = new HashMap<>();

    public static void init(PhantomMarket plugin) {
        registry.clear();

        // Регистрация всех 17 предметов
        register("dust", VoidDust.getItem(plugin));
        register("shard", EtherItems.getShard(plugin));
        register("head", PhantomHead.getItem(plugin));
        register("phantom-contract", PhantomContractItem.getItem(plugin));
        register("reaper", PhantomReaper.getItem(plugin));
        register("echoarrow", EchoArrow.getItem(plugin));
        register("dash", DashSpell.getItem(plugin));
        register("pulse", EchoPulse.getItem(plugin));
        register("ether", EtherItems.getShard(plugin));
        register("totem", InsomniaTotem.getItem(plugin));
        register("mask", PhantomMask.getItem(plugin));
        register("cloak", ShadowCloak.getItem(plugin));
        register("amulet", SleepAmulet.getItem(plugin));
        register("dagger", TraitorDagger.getItem(plugin));
        register("elixir", VoidElixir.getItem(plugin));
        register("pickaxe", VoidPickaxe.getItem(plugin));
        register("sphere", WindSphere.getItem(plugin));
        register("warden-contract", WardenContract.getItem(plugin));
    }

    private static void register(String id, ItemStack item) {
        if (item != null) {
            registry.put(id.toLowerCase(), item);
        }
    }

    public static ItemStack getItem(String id) {
        ItemStack item = registry.get(id.toLowerCase());
        return (item != null) ? item.clone() : null;
    }

    public static Set<String> getAllIds() {
        return registry.keySet();
    }
}