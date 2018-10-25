package main.java.me.wertik.enchants.objects;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class EntityDamageEnchantment extends Enchantment {

    public abstract void onDamage(EntityDamageByEntityEvent e);
}
