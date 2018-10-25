package me.wertik.enchants.enchantments.blockbreak;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.DataHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Furnace extends me.wertik.enchants.objects.Enchantment {

    private DataHandler dataHandler;
    private Main plugin;

    public Furnace() {
        plugin = Main.getInstance();
        dataHandler = plugin.getDataHandler();
    }

    public String name() {
        return "furnace";
    }

    public void onBlockBreak(BlockBreakEvent e) {

    }

    public void onDamage(EntityDamageByEntityEvent e) {

    }
}
