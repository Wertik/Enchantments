package main.java.me.wertik.enchants.handlers;

import main.java.me.wertik.enchants.ConfigLoader;
import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.objects.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BookManager {

    private Main plugin;
    private ConfigLoader configLoader;
    private EnchantManager enchantManager;
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

    public ItemStack getBook(Enchantment enchant) {

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta itemMeta = book.getItemMeta();

        List<String> lore = configLoader.getFinalStringList("Book.lore", enchant);
        String name = configLoader.getFinalString("Book.name", enchant);

        itemMeta.setDisplayName(belChar + name);
        itemMeta.setLore(lore);

        book.setItemMeta(itemMeta);

        return book;
    }

    public boolean isBook(ItemStack item) {
        if (item.hasItemMeta())
            return item.getItemMeta().getDisplayName().contains(belChar);

        return false;
    }

    public Enchantment getEnchantFromBook(ItemStack item) {

        for (Enchantment enchant : enchantManager.getEnchantments()) {
            ItemStack book = getBook(enchant);

            if (book.isSimilar(item)) {
                return enchant;
            }
        }

        return null;
    }
}
