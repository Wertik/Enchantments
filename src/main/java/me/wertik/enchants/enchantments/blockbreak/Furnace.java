package main.java.me.wertik.enchants.enchantments.blockbreak;

import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.handlers.DataHandler;
import main.java.me.wertik.enchants.objects.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Furnace extends Enchantment {

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
