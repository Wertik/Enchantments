package main.java.me.wertik.enchants.enchantments.entitydamage;

import main.java.me.wertik.enchants.objects.EntityDamageEnchantment;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BonusDamage extends EntityDamageEnchantment {

    public void onDamage(EntityDamageByEntityEvent e) {

    }

    public String name() {
        return "rekt";
    }

    public String type() {
        return "entityDamage";
    }
}