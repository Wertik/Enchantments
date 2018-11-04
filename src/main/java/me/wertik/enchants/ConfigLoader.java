package me.wertik.enchants;

import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    private FileConfiguration config;
    private Main plugin;
    private YamlConfiguration languageYaml;
    private File languageFile;

    ConfigLoader() {
        plugin = Main.getInstance();
    }

    public void loadYamls() {

        // CF
        File configFile = new File(plugin.getDataFolder() + "/config.yml");

        if (!configFile.exists()) {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + "§aGenerated default §f" + configFile.getName());
        }

        config = plugin.getConfig();

        // Language.yml

        languageFile = new File(plugin.getDataFolder() + "/language.yml");

        if (!languageFile.exists()) {
            plugin.saveResource("language.yml", false);
            languageYaml = YamlConfiguration.loadConfiguration(languageFile);
            languageYaml.options().copyDefaults(true);
            try {
                languageYaml.save(languageFile);
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + "§cCould not save the file §f" + languageFile.getName() + "§c, that's bad tho.");
            }
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginPrefix() + "§aGenerated default §f" + languageFile.getName());
        } else
            languageYaml = YamlConfiguration.loadConfiguration(languageFile);
    }

    public String getMessage(String name) {
        return format(languageYaml.getString(name));
    }

    private String getStringFromConfig(String path) {
        return config.getString(path);
    }

    private List<String> getStringListFromConfig(String path) {
        return config.getStringList(path);
    }

    public List<String> getFinalStringList(String path, Enchantment enchant) {
        return formatList(parseList(getStringListFromConfig(path), enchant));
    }

    public List<String> getFinalStringList(String path, Enchantment enchant, int level) {
        return formatList(parseList(getStringListFromConfig(path), enchant, level));
    }

    public String getFinalString(String path, Enchantment enchant) {
        return format(parse(getStringFromConfig(path), enchant));
    }

    public String getFinalString(String path, Enchantment enchant, int level) {
        return format(parse(getStringFromConfig(path), enchant, level));
    }

    /*
     * Placeholders:
     *
     * %enchant_name%
     * %enchant_display_name%
     *
     * -------------- ?
     * %book_success%
     * %book_destroy%
     *
     * */

    private String parse(String msg, Enchantment enchant) {
        if (msg.contains("%enchant_name%"))
            msg = msg.replace("%enchant_name%", enchant.name());
        if (msg.contains("%enchant_display_name%"))
            msg = msg.replace("%enchant_display_name%", enchant.displayName());
        return msg;
    }

    public String parse(String msg, Enchantment enchant, int level) {
        msg = parse(msg, enchant);
        if (msg.contains("%enchant_level%"))
            msg = msg.replace("%enchant_level%", Utils.RomanNumerals(level));
        return msg;
    }

    private List<String> parseList(List<String> list, Enchantment enchant) {

        List<String> outList = new ArrayList<>();

        for (String line : list) {
            if (line.contains("%enchant_description%")) {
                outList.addAll(formatList(enchant.description()));
                continue;
            }
            outList.add(format(parse(line, enchant)));
        }

        return outList;
    }

    private List<String> parseList(List<String> list, Enchantment enchant, int level) {
        List<String> outList = new ArrayList<>();

        list = parseList(list, enchant);

        for (String line : list) {
            outList.add(parse(line, enchant, level));
        }

        return outList;
    }

    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    private List<String> formatList(List<String> list) {
        List<String> outList = new ArrayList<>();

        for (String line : list) {
            outList.add(format(line));
        }
        return outList;
    }
}
