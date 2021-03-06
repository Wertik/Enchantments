package me.wertik.enchants.commands;

import me.wertik.enchants.ConfigLoader;
import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.BookManager;
import me.wertik.enchants.handlers.EnchantManager;
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
    private ConfigLoader configLoader;

    public Commands() {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
        bookManager = plugin.getBookManager();
        messanger = new Messanger();
        configLoader = plugin.getConfigLoader();
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

    @Deprecated
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
                        if (args.length < 3) {
                            p.sendMessage(configLoader.getMessage("not-enough-args"));
                            return false;
                        } else if (args.length > 3) {
                            p.sendMessage(configLoader.getMessage("too-many-args"));
                            return false;
                        }

                        // Enchant existence check..
                        if (!enchantManager.getEnchantNames().contains(args[1])) {
                            p.sendMessage(configLoader.getMessage("does-not-exist"));
                            return false;
                        }

                        // Validate level
                        int level;
                        try {
                            level = Integer.valueOf(args[2]);
                        } catch (NumberFormatException e) {
                            p.sendMessage(configLoader.getMessage("not-a-number"));
                            return false;
                        }

                        // Max level check
                        if (enchantManager.getEnchantByName(args[1]).maxLevel() < level) {
                            p.sendMessage(configLoader.getMessage("over-max-level"));
                            return false;
                        }

                        // Success!
                        p.getInventory().addItem(bookManager.createBook(enchantManager.getEnchantByName(args[1]), level));
                        p.sendMessage(configLoader.getMessage("given"));

                        break;
                    default:
                        p.sendMessage(configLoader.getMessage("not-a-command"));
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
                        if (args.length < 3) {
                            p.sendMessage(configLoader.getMessage("not-enough-args"));
                            return false;
                        } else if (args.length > 3) {
                            p.sendMessage(configLoader.getMessage("too-many-args"));
                            return false;
                        }

                        // Enchant existence check..
                        if (!enchantManager.getEnchantNames().contains(args[1])) {
                            p.sendMessage(configLoader.getMessage("does-not-exist"));
                            return false;
                        }

                        Enchantment enchant = enchantManager.getEnchantByName(args[1]);

                        // Empty hand? q.q
                        if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            p.sendMessage(configLoader.getMessage("empty-hand"));
                            return false;
                        }

                        // If hand is not empty and enchantable..
                        if (!enchantManager.isEnchantable(enchant, p.getInventory().getItemInMainHand())) {
                            p.sendMessage(configLoader.getMessage("not-enchantable"));
                            return false;
                        }

                        // Validate level
                        int level;
                        try {
                            level = Integer.valueOf(args[2]);
                        } catch (NumberFormatException e) {
                            p.sendMessage(configLoader.getMessage("not-a-number"));
                            return false;
                        }

                        // Success!
                        p.getInventory().setItemInMainHand(enchantManager.enchantItem(p.getInventory().getItemInMainHand(), enchant, level));
                        p.sendMessage(configLoader.getMessage("enchanted"));
                        break;
                    case "list":

                        // Argument lenght check..
                        if (args.length < 2) {
                            p.sendMessage(configLoader.getMessage("not-enough-args"));
                            return false;
                        } else if (args.length > 2) {
                            p.sendMessage(configLoader.getMessage("too-many-args"));
                            return false;
                        }

                        // hand or all?
                        if (args[1].equalsIgnoreCase("all")) {
                            messanger.list(p);
                        } else if (args[1].equalsIgnoreCase("hand")) {

                            // Empty hand? q.q
                            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                p.sendMessage(configLoader.getMessage("empty-hand"));
                                return false;
                            }

                            // Enchanted?
                            if (!enchantManager.isEnchanted(p.getInventory().getItemInMainHand())) {
                                p.sendMessage(configLoader.getMessage("not-enchanted"));
                                return false;
                            }

                            p.sendMessage("§6Enchants on your item:");
                            for (Enchantment enchant1 : enchantManager.getEnchantsOnItem(p.getInventory().getItemInMainHand()).keySet()) {
                                p.sendMessage("§f" + enchant1.name());
                            }
                        } else
                            p.sendMessage(configLoader.getMessage("hand-or-all"));
                        break;
                    case "remove":

                        // Argument lenght check..
                        if (args.length < 2) {
                            p.sendMessage(configLoader.getMessage("not-enough-args"));
                            return false;
                        } else if (args.length > 2) {
                            p.sendMessage(configLoader.getMessage("too-many-args"));
                            return false;
                        }

                        // Empty hand? q.q
                        if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            p.sendMessage(configLoader.getMessage("empty-hand"));
                            return false;
                        }

                        // Enchanted?
                        if (!enchantManager.isEnchanted(p.getInventory().getItemInMainHand())) {
                            p.sendMessage(configLoader.getMessage("not-enchanted"));
                            return false;
                        }

                        // Remove
                        enchantManager.removeEnchant(p.getInventory().getItemInMainHand(), enchantManager.getEnchantByName(args[1]));
                        p.sendMessage(configLoader.getMessage("enchants-cleared"));
                        break;
                    case "clear":

                        // Argument lenght check..
                        if (args.length > 1) {
                            p.sendMessage(configLoader.getMessage("too-many-args"));
                            return false;
                        }

                        // Empty hand? q.q
                        if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            p.sendMessage(configLoader.getMessage("empty-hand"));
                            return false;
                        }

                        // Enchanted?
                        if (!enchantManager.isEnchanted(p.getInventory().getItemInMainHand())) {
                            p.sendMessage(configLoader.getMessage("not-enchanted"));
                            return false;
                        }

                        p.setItemInHand(enchantManager.clearEnchants(p.getInventory().getItemInMainHand()));
                        p.sendMessage(configLoader.getMessage("enchants-cleared"));

                        break;
                    case "reload":
                        configLoader.loadYamls();
                        Main.getInstance().getDataHandler().loadYamls();
                        plugin.reloadConfig();
                        p.sendMessage("§6Reloaded boss!");
                        break;
                    case "info":
                        messanger.info(p);
                        break;
                    default:
                        p.sendMessage(configLoader.getMessage("not-a-command"));
                        return false;
                }
            }

        } else
            sender.sendMessage("§cCommands are only for players. Noone likes consoles.");
        return false;
    }
}
