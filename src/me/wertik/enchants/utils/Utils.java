package me.wertik.enchants.utils;

import org.bukkit.ChatColor;

public class Utils {

    // Translate color codes.
    public String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    // For future placeholder support.
    public String parse(String msg) {
        return null;
    }

}
