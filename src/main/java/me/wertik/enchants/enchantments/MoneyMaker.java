package me.wertik.enchants.enchantments;

import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoneyMaker extends Enchantment {

    @Override
    public String name() {
        return "moneymaker";
    }

    @Override
    public boolean isToolEnchant() {
        return true;
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
    public void onToolDamage(EntityDamageByEntityEvent e, int level) {

        Player p = (Player) e.getDamager();

        Economy eco = Main.getEconomy();

        double damage = e.getDamage();

        double money = Math.random() * (damage * level);

        eco.depositPlayer(p.getName(), money);

        Location loc = e.getEntity().getLocation();
        loc.setX(loc.getX());
        loc.setZ(loc.getZ());
        loc.setY(loc.getY() + 1);

        for (int i = 0; i < 360; i += 15) {
            loc.setX(loc.getX() - Math.sin(i) * 0.75);
            loc.setZ(loc.getZ() - Math.cos(i) * 0.75);

            p.getWorld().spigot().playEffect(loc, Effect.COLOURED_DUST, 10, 15, 0, 0, 0, 1, 3, 20);
        }
    }

    @Override
    public void onTokenMove(PlayerMoveEvent e, int level) {

        Bukkit.broadcastMessage("Token move.. :");

        Player p = e.getPlayer();

        Location location = p.getLocation();

        location.getWorld().spigot().playEffect(location, Effect.CLOUD, 0, 0, 0, 0, 0, 0, 10, 20);
    }
}
