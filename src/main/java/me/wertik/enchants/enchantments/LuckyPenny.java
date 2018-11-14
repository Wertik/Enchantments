package me.wertik.enchants.enchantments;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class LuckyPenny extends Enchantment {

    @Override
    public String name() {
        return "luckypenny";
    }

    @Override
    public boolean isToolEnchant() {
        return false;
    }

    @Override
    public boolean isTokenEnchant() {
        return true;
    }

    @Override
    public boolean isArmorEnchant() {
        return false;
    }

    @Override
    public void onTokenDamageIncome(EntityDamageEvent e, int level) {

        Player p = (Player) e.getEntity();

        if (e.getDamage() >= p.getHealth()) {
            e.setCancelled(true);
            p.setHealth(p.getMaxHealth());
            p.sendMessage("§6Today is your lucky day, you live!");
            consumeToken(p);

            Location location = p.getLocation();

            for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                double radius = Math.sin(i);
                double y = Math.cos(i);
                for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                    double x = Math.cos(a) * radius;
                    double z = Math.sin(a) * radius;
                    location.add(x, y, z);
                    location.getWorld().spigot().playEffect(location, Effect.CLOUD, 0, 0, 0, 0, 0, 1, 10, 50);
                    location.subtract(x, y, z);
                }
            }
        }
    }
}
