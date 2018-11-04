package me.wertik.enchants.enchantments.entitydamage;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ThorStrike extends Enchantment {

    public String name() {
        return "thorstrike";
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent e, int level) {

        Damageable damageable = (Damageable) e.getEntity();

        e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
        damageable.damage(((Damageable) e.getEntity()).getHealth() * 0.15);
    }

    @Override
    public boolean usesEntityDamage() {
        return true;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, int level) {
    }

    @Override
    public boolean usesBlockBreak() {
        return false;
    }
}
