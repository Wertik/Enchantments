package me.wertik.enchants.enchantments.entitydamage;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BonusDamage extends Enchantment {

    public String name() {
        return "rekt";
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, int level) {

    }

    @Override
    public void onDamage(EntityDamageByEntityEvent e, int level) {

    }
}
