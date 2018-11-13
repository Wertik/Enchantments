package me.wertik.enchants.listeners;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.EnchantEventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EnchantEventListener implements Listener {

    private EnchantEventHandler enchantEventHandler;
    private Main plugin;

    public EnchantEventListener() {
        plugin = Main.getInstance();
        enchantEventHandler = plugin.getEnchantEventHandler();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (plugin.getConfig().getStringList("worlds.list").contains(e.getBlock().getWorld().getName()))
            enchantEventHandler.processEvent(e, e.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (plugin.getConfig().getStringList("worlds.list").contains(e.getDamager().getWorld().getName()))
            if (e.getDamager() instanceof Player)
                enchantEventHandler.processEvent(e, (Player) e.getDamager());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (plugin.getConfig().getStringList("worlds.list").contains(e.getPlayer().getWorld().getName()))
            enchantEventHandler.processEvent(e, e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (plugin.getConfig().getStringList("worlds.list").contains(e.getPlayer().getWorld().getName()))
            enchantEventHandler.processEvent(e, e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (plugin.getConfig().getStringList("worlds.list").contains(e.getEntity().getWorld().getName()))
            enchantEventHandler.processEvent(e, e.getEntity());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player)
            if (plugin.getConfig().getStringList("worlds.list").contains(e.getEntity().getWorld().getName()))
                enchantEventHandler.processEventDI(e, (Player) e.getEntity());

    }
}
