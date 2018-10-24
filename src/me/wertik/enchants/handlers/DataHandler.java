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
                plugin.getServer().getConsoleSender().sendMessage("§cCould not save the file §f" + enchantsFile.getName() + "§c, that's bad tho.");
            }
            plugin.getServer().getConsoleSender().sendMessage("§aGenerated default §f" + enchantsFile.getName());
        } else
            enchantsYaml = YamlConfiguration.loadConfiguration(enchantsFile);

        // Section fillout

        for (Enchantment enchant : enchantManager.getEnchantments()) {

            ConfigurationSection section;

            if (!enchantsYaml.contains(enchant.name()))
                section = enchantsYaml.createSection(enchant.name());
            else
                section = enchantsYaml.getConfigurationSection(enchant.name());

            if (!section.contains("display-name"))
                section.set("display-name", "&3" + enchant.name());

            if (!section.contains("lore-line"))
                section.set("lore-line", "&6! &3" + enchant.name());

            if (!section.contains("enchantable-item-types"))
                section.set("enchantable-item-types", new ArrayList<String>());

            ConfigurationSection conditions;

            if (!enchantsYaml.contains("conditions"))
                conditions = section.createSection("conditions");
            else
                conditions = section.getConfigurationSection("conditions");

            if (!section.contains("regions"))
                conditions.set("regions", new ArrayList<String>());

            if (!section.contains("biome-types"))
                conditions.set("biome-types", new ArrayList<String>());
        }

        try {
            enchantsYaml.save(enchantsFile);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage("§cCould not save the file §f" + enchantsFile.getName() + "§c, that's bad tho.");
        }
    }

    /*
     *
     * Hmm...
     *
     * Loading the stuff from enchants.yml
     *
     * <name>:
     *   display-name:""
     *   lore-line: "§cFurnace enchantment"
     *   enchantable-item-types:
     *   - DIAMOND_PICKAXE
     *   conditions:
     *     regions:
     *     - __global__
     *     biome-types:
     *     - PLAINS
     *
     * */

    public String getDisplayName(String name) {
        return utils.format(enchantsYaml.getString(name + ".display-name"));
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
}
