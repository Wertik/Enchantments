package me.wertik.enchants.enchantments.entitydamage;

import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MoneyMaker extends Enchantment {

    @Override
    public String name() {
        return "moneymaker";
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, int level) {
    }

    @Override
    public boolean usesBlockBreak() {
        return false;
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent e, int level) {

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
    public boolean usesEntityDamage() {
        return true;
    }
}
