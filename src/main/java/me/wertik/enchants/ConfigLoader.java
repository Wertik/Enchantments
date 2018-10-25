package me.wertik.enchants;

import me.wertik.enchants.objects.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    private FileConfiguration config;
    private Main plugin;

    public ConfigLoader() {
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

        // Language.yml (mby Messages)
    }

    public String getStringFromConfig(String path) {
        return config.getString(path);
    }

    public List<String> getStringListFromConfig(String path) {
        return config.getStringList(path);
    }

    public List<String> getFinalStringList(String path, Enchantment enchant) {
        return formatList(parseList(getStringListFromConfig(path), enchant));
    }

    public String getFinalString(String path, Enchantment enchant) {
        return format(parse(getStringFromConfig(path), enchant));
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

    private List<String> parseList(List<String> list, Enchantment enchant) {

        List<String> outList = new ArrayList<String>();

        for (String line : list) {
            if (line.contains("%enchant_description%")) {
                outList.addAll(formatList(enchant.description()));
                continue;
            }
            outList.add(format(parse(line, enchant)));
        }

        return outList;
    }

    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    private List<String> formatList(List<String> list) {
        List<String> outList = new ArrayList<String>();

        for (String line : list) {
            outList.add(format(line));
        }
        return outList;
    }
}
