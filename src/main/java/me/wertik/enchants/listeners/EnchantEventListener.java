package me.wertik.enchants.listeners;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.EnchantEventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EnchantEventListener implements Listener {

    private EnchantEventHandler enchantEventHandler;

    public EnchantEventListener() {
        enchantEventHandler = Main.getInstance().getEnchantEventHandler();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        enchantEventHandler.processEvent(e, e.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player)
            enchantEventHandler.processEvent(e, (Player) e.getDamager());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        enchantEventHandler.processEvent(e, e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        enchantEventHandler.processEvent(e, e.getPlayer());
    }
}
