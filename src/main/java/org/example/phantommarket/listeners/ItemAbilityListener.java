package org.example.phantommarket.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.example.phantommarket.PhantomMarket;
import org.example.phantommarket.items.*;

import java.util.HashMap;
import java.util.UUID;

public class ItemAbilityListener implements Listener {
    private final PhantomMarket plugin;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public ItemAbilityListener(PhantomMarket plugin) {
        this.plugin = plugin;
    }

    private boolean isOffCooldown(Player player, String key, int seconds) {
        UUID id = player.getUniqueId();
        long now = System.currentTimeMillis();
        if (cooldowns.containsKey(id) && cooldowns.get(id) > now) {
            player.sendMessage("§cСпособность еще перезаряжается!");
            return false;
        }
        cooldowns.put(id, now + (seconds * 1000L));
        return true;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        var data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey idKey = new NamespacedKey(plugin, "item_id");
        if (!data.has(idKey, PersistentDataType.STRING)) return;

        String itemId = data.get(idKey, PersistentDataType.STRING);
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (itemId) {
                case "dash_spell" -> { if (isOffCooldown(player, "dash", 5)) DashSpell.execute(player); }
                case "echo_pulse" -> { if (isOffCooldown(player, "pulse", 10)) EchoPulse.execute(player); }
                case "sleep_amulet" -> { if (isOffCooldown(player, "sleep", 20)) SleepAmulet.execute(player); }
                case "shadow_cloak" -> { if (isOffCooldown(player, "cloak", 30)) ShadowCloak.activate(player); }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker) || !(event.getEntity() instanceof LivingEntity victim)) return;
        ItemStack item = attacker.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;

        String itemId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "item_id"), PersistentDataType.STRING);
        if ("traitor_dagger".equals(itemId)) {
            event.setDamage(TraitorDagger.processBackstab(attacker, victim, event.getDamage()));
        } else if ("phantom_reaper".equals(itemId)) {
            PhantomReaper.applyEffect(attacker, victim);
        }
    }

    @EventHandler
    public void onProjectile(ProjectileHitEvent event) {
        Entity projectile = event.getEntity();
        if (!(projectile instanceof Projectile proj) || !(proj.getShooter() instanceof Player)) return;

        ItemStack item = null;

        if (projectile instanceof Snowball snowball) {
            item = snowball.getItem();
        } else if (projectile instanceof AbstractArrow arrow) {
            item = arrow.getItemStack();
        } else if (projectile instanceof EnderPearl pearl) {
            item = pearl.getItem();
        }

        if (item != null && item.hasItemMeta()) {
            String itemId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "item_id"), PersistentDataType.STRING);
            if ("echo_arrow".equals(itemId)) {
                EchoArrow.playEchoEffect(event.getHitEntity() != null ? event.getHitEntity() : event.getEntity());
            } else if ("wind_sphere".equals(itemId)) {
                WindSphere.explode(event.getHitEntity() != null ? event.getHitEntity() : event.getEntity());
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;
        String itemId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "item_id"), PersistentDataType.STRING);
        if ("void_pickaxe".equals(itemId)) VoidPickaxe.applyVoidPower(event.getPlayer());
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (!item.hasItemMeta()) return;
        String itemId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "item_id"), PersistentDataType.STRING);
        if ("void_elixir".equals(itemId)) VoidElixir.applyEffect(event.getPlayer());
    }

    @EventHandler
    public void onResurrect(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.TOTEM_OF_UNDYING) item = player.getInventory().getItemInOffHand();
        if (item != null && item.hasItemMeta()) {
            String itemId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "item_id"), PersistentDataType.STRING);
            if ("insomnia_totem".equals(itemId)) InsomniaTotem.applyPower(player);
        }
    }
}