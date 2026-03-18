package org.example.phantommarket.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.config.ItemData;
import java.util.List;

public class DashSpell {
    public static ItemStack getItem(PhantomMarket plugin) {
        ItemData data = new ItemData(plugin);
        ItemStack item = new ItemStack(Material.FEATHER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(data.getDashName());
            List<String> lore = data.getDashLore();
            lore.add("");
            lore.add("§7Перезарядка: §f5 сек.");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Метод для активации "фишки" рывка
    public static void execute(Player player) {
        // Толкаем игрока вперед по направлению взгляда
        Vector direction = player.getLocation().getDirection().multiply(1.6).setY(0.4);
        player.setVelocity(direction);

        // Красивые эффекты
        player.playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 1.0f, 1.5f);
        player.sendMessage("§d✨ Вы использовали рывок!");
    }
}