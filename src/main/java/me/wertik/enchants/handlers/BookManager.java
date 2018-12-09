package me.wertik.enchants.handlers;

import me.mrwener.enchants.nbt.NBTEditor;
import me.mrwener.enchants.nbt.NBTUtils;
import me.wertik.enchants.ConfigLoader;
import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BookManager {

    private Main plugin;
    private ConfigLoader configLoader;
    private EnchantManager enchantManager;
    private String belChar;
    private Utils utils;

    public BookManager() {
        plugin = Main.getInstance();
        configLoader = plugin.getConfigLoader();
        enchantManager = plugin.getEnchantManager();
        utils = plugin.getUtils();
        belChar = ChatColor.translateAlternateColorCodes('&', "&b&e&l&c&a&r");
    }

    /*
     * Enchant book manager
     *
     * name == book prefix + its display name
     *
     * lore == config
     *
     * success rate == random < 100
     *
     * destroy == 100 - success rate
     *
     * NBT data:
     * "book", "enchant_name"
     * "success_rate", int
     * "destroy_rate", int
     *
     * */

    public ItemStack createBook(Enchantment enchant, int level) {

        // ItemStack

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

        double success = Math.random() * 100;

        ItemMeta itemMeta = book.getItemMeta();

        List<String> lore = replaceRates(configLoader.getFinalStringList("book.lore", enchant, level), success);
        String name = configLoader.getFinalString("book.name", enchant, level);

        itemMeta.setDisplayName(belChar + name);
        itemMeta.setLore(lore);

        book.setItemMeta(itemMeta);

        // NBT

        book = NBTEditor.writeNBT(book, "book", enchant.name());

        book = NBTEditor.writeNBT(book, "success_rate", String.valueOf(success));

        book = NBTEditor.writeNBT(book, "destroy_rate", String.valueOf(100 - success));

        book = NBTEditor.writeNBT(book, "level", String.valueOf(level));

        return book;
    }

    private List<String> replaceRates(List<String> list, double success) {

        List<String> newList = new ArrayList<>();

        for (String line : list) {
            if (line.contains("%success_rate%"))
                line = line.replace("%success_rate%", utils.formatDouble(success));
            if (line.contains("%destroy_rate%"))
                line = line.replace("%destroy_rate%", utils.formatDouble(100 - success));
            newList.add(line);
        }

        return newList;
    }

    public boolean isSuccessful(double success) {

        double run = Math.random() * 100;

        return run < success;
    }

    public boolean isBook(ItemStack item) {
        return NBTEditor.hasNBTTag(item, "book");
    }

    public Enchantment getEnchantFromBook(ItemStack item) {

        String enchantName = NBTEditor.getNBT(item, "book");

        return enchantManager.getEnchantByName(NBTUtils.strip(enchantName));
    }

    public int getLevelFromBook(ItemStack item) {
        String levelString = NBTUtils.strip(NBTEditor.getNBT(item, "level"));

        return Integer.valueOf(levelString);
    }
}
