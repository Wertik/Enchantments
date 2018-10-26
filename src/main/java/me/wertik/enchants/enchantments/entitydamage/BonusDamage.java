package me.wertik.enchants.enchantments.entitydamage;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BonusDamage extends Enchantment {

    public void onDamage(EntityDamageByEntityEvent e) {

    }

    @Override
    public void onInteract(PlayerInteractEvent e) {

    }

    public String name() {
        return "rekt";
    }

    public void onBlockBreak(BlockBreakEvent e) {

    }
}
