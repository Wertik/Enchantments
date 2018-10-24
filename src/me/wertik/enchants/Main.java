package me.wertik.enchants;

import me.wertik.enchants.commands.Commands;
import me.wertik.enchants.handlers.DataHandler;
import me.wertik.enchants.handlers.EnchantManager;
import me.wertik.enchants.listeners.BlockBreak;
import me.wertik.enchants.listeners.EntityDamage;
import me.wertik.enchants.utils.Utils;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private ConfigLoader configLoader;
    private Utils utils;
    private EnchantManager enchantManager;
    private DataHandler dataHandler;

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
     *
     * Other ideas:
     * - create enchants applicable on everything, if you have them in your inventory and a certain event happens, take action
     *
     *
     * */

    @Override
    public void onEnable() {
        ConsoleCommandSender console = getServer().getConsoleSender();

        console.sendMessage("§6Enabling Enchantments by §fWertik1206§6!");
        console.sendMessage("§f----------------------------");

        // Load stuff
        instance = this;
        configLoader = new ConfigLoader();
        utils = new Utils();
        enchantManager = new EnchantManager();
        dataHandler = new DataHandler();

        console.sendMessage("§aClasses loaded");

        enchantManager.loadEnchantments();

        console.sendMessage("§aEnchantments loaded");

        configLoader.loadYamls();
        dataHandler.loadYamls();

        console.sendMessage("§aFiles loaded");

        getCommand("enchants").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);

        console.sendMessage("§aListeners and commands registered");

        console.sendMessage("§f----------------------------");
        console.sendMessage("§6Done...");
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender console = getServer().getConsoleSender();

        console.sendMessage("§6Disabling Enchantments by §fWertik1206§6!");
        console.sendMessage("§f----------------------------");

        // Do stuff
        console.sendMessage("§cNothing to disable. Why is this segment even here?");

        console.sendMessage("§f----------------------------");
        console.sendMessage("§6Done... bye cruel world.");
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public Utils getUtils() {
        return utils;
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }
}
