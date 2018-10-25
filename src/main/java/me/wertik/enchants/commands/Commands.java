package me.wertik.enchants.commands;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.BookManager;
import me.wertik.enchants.handlers.EnchantManager;
import me.wertik.enchants.objects.Book;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private Main plugin;
    private EnchantManager enchantManager;
    private BookManager bookManager;
    private Messanger messanger;

    public Commands() {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
        bookManager = plugin.getBookManager();
        messanger = new Messanger();
    }

    /*
     * Commands: (planned)
     *
     * hand == mainHandItem
     * - ignores air (empty hand)
     *
     * /enchants add <name> == add enchantment to your hand
     * /enchants list <hand/all> == list all enchants or just those on your hand
     * /enchants remove <name> == remove enchantment from your hand
     * /enchants clear == clear all enchantments from you hand
     *
     * /ebook get (enchant_name) == get enchanted book by a name
     *
     *
     * */

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("ebook")) {

                if (args.length == 0) {
                    messanger.helpBook(p);
                    return false;
                }

                switch (args[0]) {
                    case "get":

                        // Argument lenght check..
                        if (args.length < 2) {
                            p.sendMessage("§cNot enough arguments..");
                            return false;
                        } else if (args.length > 2) {
                            p.sendMessage("§cThat's too much..");
                            return false;
                        }

                        // Enchant existence check..
                        if (!enchantManager.getEnchantNames().contains(args[1])) {
                            p.sendMessage("§cThat enchant does not exist.");
                            return false;
                        }

                        // Success!
                        p.getInventory().addItem(new Book(enchantManager.getEnchantByName(args[1])).get());
                        p.sendMessage("§6Given!");

                        break;
                    default:
                        p.sendMessage("§cNot a command.. try the help page. It helps!");
                        return false;
                }

            } else if (cmd.getName().equalsIgnoreCase("enchants")) {

                if (args.length == 0) {
                    messanger.helpEnchants(p);
                    return false;
                }

                switch (args[0]) {

                    case "add":

                        // Argument lenght check..
                        if (args.length < 2) {
                            p.sendMessage("§cNot enough arguments..");
                            return false;
                        } else if (args.length > 2) {
                            p.sendMessage("§cThat's too much.. stop it.");
                            return false;
                        }

                        // Enchant existence check..
                        if (!enchantManager.getEnchantNames().contains(args[1])) {
                            p.sendMessage("§cThat enchant does not exist, wtf.");
                            return false;
                        }

                        Enchantment enchant = enchantManager.getEnchantByName(args[0]);

                        // Empty hand? q.q
                        if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            p.sendMessage("§cEmpty hand? Well, that's a problem..");
                            return false;
                        }

                        // If hand is not empty and enchantable..
                        if (!enchantManager.isEnchantable(enchant, p.getInventory().getItemInMainHand())) {
                            p.sendMessage("§cThat item is not enchantable.. what are you trying on me..");
                            return false;
                        }

                        // Success!
                        p.getInventory().setItemInMainHand(enchantManager.enchantItem(p.getInventory().getItemInMainHand(), enchant));
                        p.sendMessage("§6Item enchanted. Check out what it does!");

                        break;
                    case "list":

                        // Argument lenght check..
                        if (args.length < 2) {
                            p.sendMessage("§cNot enough arguments..");
                            return false;
                        } else if (args.length > 2) {
                            p.sendMessage("§cThat's too much..");
                            return false;
                        }

                        // hand or all?
                        if (args[1].equalsIgnoreCase("all")) {
                            messanger.list(p);
                        } else if (args[1].equalsIgnoreCase("hand")) {

                            // Empty hand? q.q
                            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                p.sendMessage("§cEmpty hand? Well, that's a problem..");
                                return false;
                            }

                            p.sendMessage("§6Enchants on your item:");
                            for (Enchantment enchant1 : enchantManager.getEnchantsOnItem(p.getInventory().getItemInMainHand())) {
                                p.sendMessage("§f" + enchant1.name());
                            }
                        } else {
                            p.sendMessage("§cThere are 2 options. How hard is it? HAND/ALL");
                        }
                        break;
                    case "remove":

                        // Argument lenght check..
                        if (args.length < 2) {
                            p.sendMessage("§cNot enough arguments..");
                            return false;
                        } else if (args.length > 2) {
                            p.sendMessage("§cThat's too much.. stop it.");
                            return false;
                        }

                        // Empty hand? q.q
                        if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            p.sendMessage("§cEmpty hand? Well, that's a problem..");
                            return false;
                        }

                        // Enchanted?
                        if (!enchantManager.isEnchanted(p.getInventory().getItemInMainHand())) {
                            p.sendMessage("§cItem is not enchanted.. ");
                            return false;
                        }

                        // Remove
                        enchantManager.removeEnchant(p.getInventory().getItemInMainHand());
                        p.sendMessage("§6Done. It's gone.");

                        break;
                    case "clear":

                        // Argument lenght check..
                        if (args.length < 1) {
                            p.sendMessage("§cNot enough arguments..");
                            return false;
                        } else if (args.length > 1) {
                            p.sendMessage("§cThat's too much.. stop it.");
                            return false;
                        }

                        // Empty hand? q.q
                        if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            p.sendMessage("§cEmpty hand? Well, that's a problem..");
                            return false;
                        }

                        // Enchanted?
                        if (!enchantManager.isEnchanted(p.getInventory().getItemInMainHand())) {
                            p.sendMessage("§cItem is not enchanted.. ");
                            return false;
                        }

                        p.setItemInHand(enchantManager.clearEnchants(p.getInventory().getItemInMainHand()));
                        p.sendMessage("§6Done. Everything is cleared..");

                        break;
                    default:
                        p.sendMessage("§cNot a command.. try the help page. It helps!");
                        return false;
                }

            }

        } else
            sender.sendMessage("§cCommands are only for players. Noone likes consoles.");
        return false;
    }
}
