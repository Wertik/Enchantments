package main.java.me.wertik.enchants.commands;

import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.handlers.BookManager;
import main.java.me.wertik.enchants.handlers.EnchantManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private Main plugin;
    private EnchantManager enchantManager;
    private BookManager bookManager;

    public Commands() {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
        bookManager = plugin.getBookManager();
    }

    /*
     * Commands:
     *
     * hand == mainHandItem
     * - ignores air (empty hand)
     *
     * /enchants add <name> == add enchantment to your hand
     * /enchants list <hand/all> == list all enchants or just those on your hand
     * /enchants remove <name> == remove enchantment from your hand
     * /enchants clear == clear all enchantments from you hand
     *
     * */

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args[0].equalsIgnoreCase("addlore")) {
                enchantManager.enchantItem(p.getInventory().getItemInMainHand(), enchantManager.getEnchantByName(args[1]));
                p.sendMessage("§3Enchanted! At least it should..");
            } else if (args[0].equalsIgnoreCase("getbook")) {
                p.getInventory().addItem(bookManager.getBook(enchantManager.getEnchantByName(args[1])));
                p.sendMessage("§3Book given?");
            }
        }
        return false;
    }
}