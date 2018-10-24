package me.wertik.enchants.enchantments.blockbreak;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.DataHandler;
import me.wertik.enchants.objects.BlockBreakEnchantment;
import org.bukkit.event.block.BlockBreakEvent;

public class Furnace extends BlockBreakEnchantment {

    private DataHandler dataHandler;
    private Main plugin;

    public Furnace() {
        plugin = Main.getInstance();
        dataHandler = plugin.getDataHandler();
    }

    public String name() {
        return "furnace";
    }

    public String type() {
        return "blockBreak";
    }

    public void onBlockBreak(BlockBreakEvent e) {

    }
}
