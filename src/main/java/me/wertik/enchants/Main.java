package me.wertik.enchants;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.wertik.enchants.commands.Commands;
import me.wertik.enchants.enchantments.blockbreak.Furnace;
import me.wertik.enchants.enchantments.entitydamage.MoneyMaker;
import me.wertik.enchants.enchantments.entitydamage.PumpkinReplace;
import me.wertik.enchants.enchantments.entitydamage.ThorStrike;
import me.wertik.enchants.handlers.BookManager;
import me.wertik.enchants.handlers.DataHandler;
import me.wertik.enchants.handlers.EnchantEventHandler;
import me.wertik.enchants.handlers.EnchantManager;
import me.wertik.enchants.listeners.EnchantEventListener;
import me.wertik.enchants.listeners.Inventory;
import me.wertik.enchants.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private ConfigLoader configLoader;
    private Utils utils;
    private EnchantManager enchantManager;
    private DataHandler dataHandler;
    private String pluginPrefix = "";
    private BookManager bookManager;
    private static Economy econ = null;
    private EnchantEventHandler enchantEventHandler;

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

    public static Economy getEconomy() {
        return econ;
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

    @Override
    public void onEnable() {

        pluginPrefix = "§r[Enchants] ";

        info("§6Enabling Enchantments by §fWertik1206§6!");
        info("§f----------------------------");

        // Supported plugins

        if (!setupEconomy())
            info("§cNo Vault dependency found, enchants using money won't work.");
        else
            info("§aVault hooked.");

        // WorldEdit

        if (this.getWorldEdit() == null) {
            info("§cWorldEdit is needed to run WorldGuard.");
        } else
            info("§aWorld Edit hooked successfuly.");

        // WorldGuard

        if (this.getWorldGuard() == null) {
            info("§cWorldGuard not found, region support won't work.");
        } else
            info("§aWorld Guard hooked successfuly.");

        // Load stuff
        instance = this;
        configLoader = new ConfigLoader();
        utils = new Utils();
        enchantManager = new EnchantManager();
        dataHandler = new DataHandler();
        bookManager = new BookManager();
        enchantEventHandler = new EnchantEventHandler();

        info("§aClasses loaded");

        // Load built-in enchants.

        new Furnace().hook();
        new ThorStrike().hook();
        new PumpkinReplace().hook();
        new MoneyMaker().hook();

        info("§aEnchantments loaded");

        configLoader.loadYamls();
        dataHandler.loadYamls();

        info("§aFiles loaded");

        getCommand("enchants").setExecutor(new Commands());
        getCommand("ebook").setExecutor(new Commands());
        // Enchant listeners
        getServer().getPluginManager().registerEvents(new EnchantEventListener(), this);
        // Book apply listener
        getServer().getPluginManager().registerEvents(new Inventory(), this);

        info("§aListeners and commands registered");

        info("§f----------------------------");
        info("§6Done... version §f" + getDescription().getVersion());
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
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

    public WorldEditPlugin getWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null;
        }

        return (WorldEditPlugin) plugin;
    }

    public EnchantEventHandler getEnchantEventHandler() {
        return enchantEventHandler;
    }
}
