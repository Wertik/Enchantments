package me.wertik.enchants;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    private FileConfiguration config;
    private Main plugin;

    public ConfigLoader() {
        plugin = Main.getInstance();
    }

    public void loadYamls() {

        // Config.yml (global settings)

        File configFile = new File(plugin.getDataFolder() + "/config.yml");

        if (!configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);

            config.options().copyDefaults(true);
            try {
                config.save(configFile);
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage("§cCould not save §f" + configFile.getName());
            }
        } else
            config = YamlConfiguration.loadConfiguration(configFile);

        // Enchants.yml (names, display, etc..)

        // Language.yml (mby Messages)
    }
}
