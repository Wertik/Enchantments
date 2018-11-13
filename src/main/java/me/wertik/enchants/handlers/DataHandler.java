package me.wertik.enchants.handlers;

import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private Main plugin;
    private Utils utils;
    private EnchantManager enchantManager;
    private YamlConfiguration enchantsYaml;
    private File enchantsFile;

    public DataHandler() {
        plugin = Main.getInstance();
        utils = plugin.getUtils();
        enchantManager = plugin.getEnchantManager();
    }

    public void loadYamls() {

        enchantsFile = new File(plugin.getDataFolder() + "/enchants.yml");

        if (!enchantsFile.exists()) {
            plugin.saveResource("enchants.yml", false);
            enchantsYaml = YamlConfiguration.loadConfiguration(enchantsFile);
            enchantsYaml.options().copyDefaults(true);
            try {
                enchantsYaml.save(enchantsFile);
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + "§cCould not save the file §f" + enchantsFile.getName() + "§c, that's bad tho.");
            }
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + "§aGenerated default §f" + enchantsFile.getName());
        } else
            enchantsYaml = YamlConfiguration.loadConfiguration(enchantsFile);

        // Section fillout

        for (Enchantment enchant : enchantManager.getEnchantments()) {

            ConfigurationSection section = checkSection(enchantsYaml, enchant.name());

            checkOption(section, "display-name", "&3" + enchant.name());
            checkOption(section, "max-level", 1);
            checkOption(section, "chance", 0.5);
            checkOption(section, "lore-line", "&3" + enchant.name() + " %enchant_level%");
            checkOption(section, "enchantable-item-types", new ArrayList<>());
            checkOption(section, "description", new ArrayList<>());

            section = checkSection(enchantsYaml, enchant.name() + ".conditions");

            checkOption(section, "regions", new ArrayList<>());
            checkOption(section, "biome-types", new ArrayList<>());
            checkOption(section, "worlds", new ArrayList<>());
        }

        try {
            enchantsYaml.save(enchantsFile);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + "§cCould not save the file §f" + enchantsFile.getName() + "§c, that's bad tho.");
        }
    }

    private void checkOption(ConfigurationSection section, String name, String defaultValue) {
        if (!section.contains(name))
            section.set(name, defaultValue);
    }

    private void checkOption(ConfigurationSection section, String name, int defaultValue) {
        if (!section.contains(name))
            section.set(name, defaultValue);
    }

    private void checkOption(ConfigurationSection section, String name, double defaultValue) {
        if (!section.contains(name))
            section.set(name, defaultValue);
    }

    private void checkOption(ConfigurationSection section, String name, List<String> defaultValue) {
        if (!section.contains(name))
            section.set(name, defaultValue);
    }

    private ConfigurationSection checkSection(YamlConfiguration yaml, String path) {
        ConfigurationSection section;
        if (!yaml.contains(path))
            section = yaml.createSection(path);
        else
            section = yaml.getConfigurationSection(path);
        return section;
    }

    /*
     *
     * Hmm...
     *
     * Loading the stuff from enchants.yml
     *
     * format:
     *
     * <name>:
     *   display-name: ""
     *   chance: double
     *   lore-line: "§cFurnace enchantment"
     *   description:
     *   - ''
     *   - ''
     *   enchantable-item-types:
     *   - DIAMOND_PICKAXE
     *   conditions:
     *     regions: (not done)
     *     - __global__
     *     biome-types: (not done)
     *     - PLAINS
     *
     * */

    public String getDisplayName(String name) {
        return utils.format(enchantsYaml.getString(name + ".display-name"));
    }

    public int getMaxLevel(String name) {
        return enchantsYaml.getInt(name + ".max-level");
    }

    public double getChance(String name) {
        return enchantsYaml.getDouble(name + ".chance");
    }

    public String getLoreLine(String name) {
        return utils.format(enchantsYaml.getString(name + ".lore-line"));
    }

    public List<String> getEnchantableItemTypes(String name) {
        return enchantsYaml.getStringList(name + ".enchantable-item-types");
    }

    public List<String> getWorkableRegionNames(String name) {
        return enchantsYaml.getStringList(name + ".conditions.regions");
    }

    public List<String> getWorkableBiomeTypes(String name) {
        return enchantsYaml.getStringList(name + ".conditions.biome-types");
    }

    public List<String> getWorkableWorldNames(String name) {
        return enchantsYaml.getStringList(name + ".conditions.worlds");
    }

    public List<String> getDescription(String name) {
        return enchantsYaml.getStringList(name + ".description");
    }
}
