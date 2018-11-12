package me.wertik.enchants.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {

    // I'll use that,.. mby...
    // No ideas at all.

    // Translate color codes.
    public String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String RomanNumerals(int Int) {
        LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<>();
        roman_numerals.put("M", 1000);
        roman_numerals.put("CM", 900);
        roman_numerals.put("D", 500);
        roman_numerals.put("CD", 400);
        roman_numerals.put("C", 100);
        roman_numerals.put("XC", 90);
        roman_numerals.put("L", 50);
        roman_numerals.put("XL", 40);
        roman_numerals.put("X", 10);
        roman_numerals.put("IX", 9);
        roman_numerals.put("V", 5);
        roman_numerals.put("IV", 4);
        roman_numerals.put("I", 1);
        String res = "";
        for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
            int matches = Int / entry.getValue();
            res = res + repeat(entry.getKey(), matches);
            Int = Int % entry.getValue();
        }
        return res;
    }

    private static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public String formatDouble(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return df2.format(val).trim();
    }

    /*
     * chance ==
     * level ==
     *
     * eq.: random * ()
     *
     * */

    public boolean decide(double chance, int level) {
        double random = Math.random() * level;
        //Bukkit.broadcastMessage("Random: " + random + " Chance: " + level * chance);
        return random < level * chance;
    }

    public Material getSmelt(Material block) {
        for (MeltMaterials meltMaterials : MeltMaterials.getMeltMaterials()) {
            if (meltMaterials.getBlock().equals(block))
                return meltMaterials.getMelted();
        }
        return null;
    }
}
