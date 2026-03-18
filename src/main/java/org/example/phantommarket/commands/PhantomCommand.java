package org.example.phantommarket.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.admin.BossEditor;
import org.example.phantommarket.boss.LegendaryBoss;
import org.example.phantommarket.boss.NormalBoss;
import org.example.phantommarket.items.ItemManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhantomCommand implements CommandExecutor, TabCompleter {
    private final PhantomMarket plugin;

    public PhantomCommand(PhantomMarket plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            player.sendMessage("§5§lPhantomMarket §7| §f/pm <shop|boss|spawnmarket|bossedit|give|kill|reload>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "give":
                if (args.length < 2) {
                    player.sendMessage("§cИспользуйте: /pm give <id>");
                    return true;
                }
                ItemStack item = ItemManager.getItem(args[1]);
                if (item != null) {
                    player.getInventory().addItem(item);
                    player.sendMessage("§a[✔] Вы получили: " + (item.hasItemMeta() ? item.getItemMeta().getDisplayName() : item.getType().name()));
                } else {
                    player.sendMessage("§c[!] Предмет с ID '" + args[1] + "' не найден.");
                }
                break;

            case "spawnmarket":
                spawnMarketNPC(player);
                break;

            case "bossedit":
                BossEditor.open(player);
                break;

            case "reload":
                plugin.reloadPlugin();
                player.sendMessage("§a[✔] Плагин успешно перезагружен!");
                break;

            case "kill":
                handleKill(player);
                break;

            case "boss":
                if (args.length < 3 || !args[1].equalsIgnoreCase("spawn")) {
                    player.sendMessage("§cИспользуй: /pm boss spawn <тип>");
                    return true;
                }

                String type = args[2].toLowerCase();
                Location loc = player.getLocation();

                switch (type) {
                    case "warden-legendary":
                        new LegendaryBoss(plugin).spawnLegendary(loc);
                        player.sendMessage("§a[✔] Вызван Легендарный Варден!");
                        break;
                    case "warden-normal":
                        new LegendaryBoss(plugin).spawnNormal(loc);
                        player.sendMessage("§a[✔] Вызван Обычный Варден!");
                        break;
                    case "phantom-legendary":
                        new NormalBoss(plugin).spawnLegendary(loc);
                        player.sendMessage("§a[✔] Вызван Легендарный Фантом!");
                        break;
                    case "phantom-normal":
                        new NormalBoss(plugin).spawnNormal(loc);
                        player.sendMessage("§a[✔] Вызван Обычный Фантом!");
                        break;
                    default:
                        player.sendMessage("§cНеизвестный тип босса!");
                        break;
                }
                break;

            case "shop":
                player.performCommand("market");
                break;
        }
        return true;
    }

    private void spawnMarketNPC(Player player) {
        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setCustomName("§5§lФантомный Торговец");
        villager.setCustomNameVisible(true);
        villager.setAI(false);               // Не двигается
        villager.setInvulnerable(true);     // Не получает урон
        villager.setCollidable(false);      // Нельзя толкать (игроки проходят сквозь него)
        villager.setSilent(true);           // Не издает звуков
        villager.setPersistent(true);       // Не деспавнится при перезагрузке чанков

        player.sendMessage("§a[✔] Торговец заспавнен!");
    }

    private void handleKill(Player player) {
        int count = 0;
        for (org.bukkit.entity.Entity e : player.getWorld().getEntities()) {
            if (e.hasMetadata("boss_type") || e.getType() == EntityType.WARDEN) {
                e.remove();
                count++;
            }
        }
        player.sendMessage("§a[✔] Удалено сущностей: " + count);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return Arrays.asList("shop", "boss", "spawnmarket", "bossedit", "give", "kill", "reload");

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) return new ArrayList<>(ItemManager.getAllIds());
            if (args[0].equalsIgnoreCase("boss")) return List.of("spawn");
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("boss")) {
            return Arrays.asList("warden-legendary", "warden-normal", "phantom-legendary", "phantom-normal");
        }

        return new ArrayList<>();
    }
}