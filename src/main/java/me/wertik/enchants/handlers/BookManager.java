package me.wertik.enchants.handlers;

import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class BookManager {

    private Main plugin;
    private me.wertik.enchants.ConfigLoader configLoader;
    private me.wertik.enchants.handlers.EnchantManager enchantManager;
    private String belChar;

    public BookManager() {
        plugin = Main.getInstance();
        configLoader = plugin.getConfigLoader();
        enchantManager = plugin.getEnchantManager();
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
     *
     *
     * */

    public me.wertik.enchants.objects.Book getBook(ItemStack item) {
        // TO-DO
        return null;
    }

    public boolean isBook(ItemStack item) {
        if (item.hasItemMeta())
            return item.getItemMeta().getDisplayName().contains(belChar);

        return false;
    }

    public Enchantment getEnchantFromBook(ItemStack item) {

        for (me.wertik.enchants.objects.Enchantment enchant : enchantManager.getEnchantments()) {
            me.wertik.enchants.objects.Book book = new me.wertik.enchants.objects.Book(enchant);

            if (book.get().isSimilar(item)) {
                return enchant;
            }
        }

        return null;
    }
}
