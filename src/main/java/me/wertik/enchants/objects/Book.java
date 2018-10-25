package main.java.me.wertik.enchants.objects;

import main.java.me.wertik.enchants.ConfigLoader;
import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.handlers.BookManager;
import main.java.me.wertik.enchants.handlers.EnchantManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Book {

    private Main plugin;
    private EnchantManager enchantManager;
    private BookManager bookManager;
    private ConfigLoader configLoader;

    private String belChar;
    private Enchantment enchant;

    public Book(Enchantment enchant) {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
        bookManager = plugin.getBookManager();
        configLoader = plugin.getConfigLoader();

        belChar = ChatColor.translateAlternateColorCodes('&', "&b&e&l&c&a&r");
        this.enchant = enchant;
    }

    public ItemStack get() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta itemMeta = book.getItemMeta();

        List<String> lore = configLoader.getFinalStringList("Book.lore", enchant);
        String name = configLoader.getFinalString("Book.name", enchant);

        itemMeta.setDisplayName(belChar + name);
        itemMeta.setLore(lore);

        book.setItemMeta(itemMeta);

        return book;
    }

    public Enchantment getEnchant() {
        return enchant;
    }
}
