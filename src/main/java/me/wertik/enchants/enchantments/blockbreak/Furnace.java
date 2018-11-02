package me.wertik.enchants.enchantments.blockbreak;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class Furnace extends Enchantment {

    public String name() {
        return "furnace";
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, int level) {
        Location block = e.getBlock().getLocation();

        List<Location> locations = new ArrayList<>();

        locations.add(new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ()));
        locations.add(new Location(block.getWorld(), block.getX(), block.getY() - 1, block.getZ()));

        for (Location location : locations) {
            location.getBlock().breakNaturally();
        }
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent e, int level) {
    }
}
