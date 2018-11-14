package me.wertik.enchants.enchantments;

import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PumpkinReplace extends Enchantment {

    private Main plugin;

    public PumpkinReplace() {
        plugin = Main.getInstance();
    }

    @Override
    public String name() {
        return "halloween";
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

    @Override
    public void onToolDamage(EntityDamageByEntityEvent e, int level) {

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {

            if (e.getDamage() > ((Player) e.getEntity()).getHealth())
                return;

            Player target = (Player) e.getEntity();

            if (target.getInventory().getHelmet() != null) {
                if (!target.getInventory().getHelmet().getType().equals(Material.PUMPKIN))
                    replaceHelmet(target.getInventory().getHelmet(), target);
            } else
                replaceHelmet(target.getInventory().getHelmet(), target);

            // When you're trying to learn trigonometry on spot.

            Location loc = target.getEyeLocation();
            loc.setX(loc.getX() + 0.75);
            loc.setZ(loc.getZ() + 1.25);

            for (int i = 0; i < 360; i += 10) {
                loc.setX(loc.getX() - Math.sin(i) * 0.75);
                loc.setZ(loc.getZ() - Math.cos(i) * 0.75);

                ((Player) e.getDamager()).playEffect(loc, Effect.SMOKE, 0);
            }
        }
    }

    private void replaceHelmet(ItemStack helmet, Player player) {

        player.getInventory().setHelmet(new ItemStack(Material.PUMPKIN));

        new BukkitRunnable() {
            @Override
            public void run() {
                player.getInventory().setHelmet(helmet);
            }
        }.runTaskLaterAsynchronously(plugin, 60);
    }
}
