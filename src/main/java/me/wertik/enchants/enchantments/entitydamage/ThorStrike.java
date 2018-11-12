package me.wertik.enchants.enchantments.entitydamage;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ThorStrike extends Enchantment {

    public String name() {
        return "thorstrike";
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

        Damageable damageable = (Damageable) e.getEntity();

        e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
        damageable.damage(((Damageable) e.getEntity()).getHealth() * 0.15);
    }

}
