package main.java.me.wertik.enchants.objects;

import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.handlers.DataHandler;

import java.util.List;

public abstract class Enchantment {

    private Main plugin;
    private DataHandler dataHandler;

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

    public List<String> description() {
        return dataHandler.getDescription(name());
    }
}
