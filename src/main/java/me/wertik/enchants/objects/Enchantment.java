package me.wertik.enchants.objects;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.DataHandler;
import me.wertik.enchants.handlers.EnchantManager;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public abstract class Enchantment {

    private Main plugin;
    private DataHandler dataHandler;
    private EnchantManager enchantManager;

    public Enchantment() {
        plugin = Main.getInstance();
        dataHandler = plugin.getDataHandler();
        enchantManager = plugin.getEnchantManager();
    }

    public String line() {
        return dataHandler.getLoreLine(name());
    }

    public List<String> enchantableItemTypes() {
        return dataHandler.getEnchantableItemTypes(name());
    }

    public List<String> workableBiomeTypes() {
        return dataHandler.getWorkableBiomeTypes(name());
    }

    public List<String> workableRegionNames() {
        return dataHandler.getWorkableRegionNames(name());
    }

    public String displayName() {
        return dataHandler.getDisplayName(name());
    }

    public abstract String name();

    public List<String> description() {
        return dataHandler.getDescription(name());
    }

    // Event usage

    public abstract void onBlockBreak(BlockBreakEvent e, int level);

    public abstract boolean usesBlockBreak();

    public abstract void onDamage(EntityDamageByEntityEvent e, int level);

    public abstract boolean usesEntityDamage();

    //

    public void hook() {
        enchantManager.hookEnchant(this);
    }

    public int maxLevel() {
        return dataHandler.getMaxLevel(name());
    }

    public double chance() {
        return dataHandler.getChance(name());
    }
}
