package me.wertik.enchants.enchantments.entitydamage;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BonusDamage extends Enchantment {

    public void onDamage(EntityDamageByEntityEvent e) {

    }

    public String name() {
        return "rekt";
    }

    public void onBlockBreak(BlockBreakEvent e) {

    }
}
