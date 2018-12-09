package me.wertik.enchants.enchantments;

import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Wither extends Enchantment implements Listener {

    @Override
    public String name() {
        return "witherboss";
    }

    @Override
    public boolean isToolEnchant() {
        return true;
    }

    @Override
    public boolean isTokenEnchant() {
        return false;
    }

    @Override
    public boolean isArmorEnchant() {
        return false;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (Main.getInstance().getEnchantManager().hasEnchantment(e.getItemInHand(), this))
            e.setCancelled(true);
    }

    @Override
    public void onToolInteract(PlayerInteractEvent e, int level) {

        Entity wither = e.getPlayer().getWorld().spawnEntity(e.getClickedBlock().getLocation(), EntityType.WITHER);

        consumeTool(e.getPlayer());

        despawnWither(wither);
    }

    private void despawnWither(Entity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                entity.remove();
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 100);
    }
}
