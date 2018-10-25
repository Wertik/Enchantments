package main.java.me.wertik.enchants;

import main.java.me.wertik.enchants.commands.Commands;
import main.java.me.wertik.enchants.enchantments.blockbreak.Furnace;
import main.java.me.wertik.enchants.enchantments.entitydamage.BonusDamage;
import main.java.me.wertik.enchants.enchantments.entitydamage.ThorStrike;
import main.java.me.wertik.enchants.handlers.BookManager;
import main.java.me.wertik.enchants.handlers.DataHandler;
import main.java.me.wertik.enchants.handlers.EnchantManager;
import main.java.me.wertik.enchants.listeners.Inventory;
import main.java.me.wertik.enchants.listeners.enchantlisteners.BlockBreak;
import main.java.me.wertik.enchants.listeners.enchantlisteners.EntityDamage;
import main.java.me.wertik.enchants.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private ConfigLoader configLoader;
    private Utils utils;
    private EnchantManager enchantManager;
    private DataHandler dataHandler;
    public String pluginPrefix = "";
    private BookManager bookManager;

    public static Main getInstance() {
        return instance;
    }

    /*
     * IDEA list:
     *
     * - adding enchantment by lore
     * - command addition
     * - easy expandability
     * - average performance
     *
     * Config:
     * - check whole lore or the first line only
     *
     * Enchants.yml:
     * Conditions for enchants
     * - biomes
     * - regions
     *
     * Hooks:
     * - Vault (currency enchants)
     * - WG (region support)
     * - WE needed
     *
     * Events:
     * - entityDamage by player
     * - playerBowEvent or ArrowLand?
     * - some fish event, idk the name
     * - interact event
     *
     * Other ideas:
     * - create enchants applicable on everything, if you have them in your inventory and a certain event happens, take action
     *
     *
     * Application:
     * - a book, success/destroy
     * - anvil? inventory click?
     *
     * */

    @Override
    public void onEnable() {

        pluginPrefix = "§r[Enchants] ";

        info("§6Enabling Enchantments by §fWertik1206§6!");
        info("§f----------------------------");

        // Load stuff
        instance = this;
        configLoader = new ConfigLoader();
        utils = new Utils();
        enchantManager = new EnchantManager();
        dataHandler = new DataHandler();
        bookManager = new BookManager();

        info("§aClasses loaded");

        // TO-DO: Find a replacement for this

        new Furnace().hook();
        new ThorStrike().hook();
        new BonusDamage().hook();

        info("§aEnchantments loaded");

        configLoader.loadYamls();
        dataHandler.loadYamls();

        info("§aFiles loaded");

        getCommand("enchants").setExecutor(new Commands());
        // Enchant listeners
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        // Book apply listener
        getServer().getPluginManager().registerEvents(new Inventory(), this);

        info("§aListeners and commands registered");

        info("§f-----------------" + "-----------");
        info("§6Done... version §f" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {

        info("§6Disabling Enchantments by §fWertik1206§6!");
        info("§f----------------------------");

        // Do stuff
        info("§cNothing to disable. Why is this segment even here?");

        info("§f----------------------------");
        info("§6Done... bye cruel world.");
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public Utils getUtils() {
        return utils;
    }

    public BookManager getBookManager() {
        return bookManager;
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    private void info(String msg) {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('§', pluginPrefix + msg));
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }
}
