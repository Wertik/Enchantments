package me.wertik.enchants.commands;

import me.wertik.enchants.Main;
import me.wertik.enchants.enchantments.FurnaceEnchantment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

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
                p.getInventory().setItemInMainHand(Main.getInstance().getEnchantManager().enchantItem(p.getInventory().getItemInMainHand(), new FurnaceEnchantment()));
                p.sendMessage("§3Enchanted!");
            }
        }
        return false;
    }
}
