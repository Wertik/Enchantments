package me.wertik.enchants.objects;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.DataHandler;

import java.util.List;

public abstract class Enchantment {

    private DataHandler dataHandler;
    private Main plugin;

    public Enchantment() {
        plugin = Main.getInstance();
        dataHandler = plugin.getDataHandler();
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

    public abstract String type();
}
