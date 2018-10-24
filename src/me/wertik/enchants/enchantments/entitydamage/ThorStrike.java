package me.wertik.enchants.enchantments.entitydamage;

import me.wertik.enchants.objects.EntityDamageEnchantment;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ThorStrike extends EntityDamageEnchantment {

    public String name() {
        return "thorstrike";
    }

    public void onDamage(EntityDamageByEntityEvent e) {

    }

    public String type() {
        return "entityDamage";
    }
}
