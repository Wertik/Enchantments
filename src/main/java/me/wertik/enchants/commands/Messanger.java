package me.wertik.enchants.commands;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.EnchantManager;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.entity.Player;

public class Messanger {

    private Main plugin;
    private EnchantManager enchantManager;

    Messanger() {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
    }

    void helpEnchants(Player p) {
        p.sendMessage("§dThis should be the help page for enchant management.");
        p.sendMessage("§7                   --------------------           ");
        p.sendMessage("§6/enchants add <name> <enchant_level> §7== §6add enchantment to your hand\n" +
                "§6/enchants list <hand/all> §7== §6list all enchants or just those on your hand\n" +
                "§6/enchants remove <name> §7== §6remove enchantment from your hand\n" +
                "§6/enchants clear §7== §6clear all enchantments from you hand\n" +
                "§6/enchants reload §7== §6reload the plugin\n" +
                "§6/enchants info §7== §6plugin info display");
        p.sendMessage("§7                   --------------------           ");
    }

    void helpBook(Player p) {
        p.sendMessage("§dThis should be the help page for enchanted books.");
        p.sendMessage("§7                   --------------------           ");
        p.sendMessage("§6/ebook get <enchant_name> <enchant_level> §7== §6get enchanted book by a name");
        p.sendMessage("§7                   --------------------           ");
    }

    public void list(Player p) {

        p.sendMessage("§dObtainable enchantments:");
        p.sendMessage("§7                   --------------------           ");

        for (Enchantment enchant : enchantManager.getEnchantments()) {
            p.sendMessage("§6" + enchant.name());
            p.sendMessage(" §6Display name: " + enchant.displayName());
            p.sendMessage(" §6Enchant lore line: " + enchant.line());

            if (!enchant.description().isEmpty()) {
                p.sendMessage(" §6Description:");
                for (String line : enchant.description()) {
                    p.sendMessage(line);
                }
            }

            if (!enchant.enchantableItemTypes().isEmpty()) {
                p.sendMessage(" §6Enchantable item types:");
                for (String line : enchant.enchantableItemTypes()) {
                    p.sendMessage("  §8- §f" + line);
                }
            }

            if (!enchant.workableBiomeTypes().isEmpty()) {
                p.sendMessage(" §6Workable biome types:");
                for (String line : enchant.workableBiomeTypes()) {
                    p.sendMessage("  §8- §f" + line);
                }
            }

            if (!enchant.workableRegionNames().isEmpty()) {
                p.sendMessage(" §6Workable regions:");
                for (String line : enchant.workableRegionNames()) {
                    p.sendMessage("  §8- §f" + line);
                }
            }
        }
        p.sendMessage("§7                   --------------------           ");
        p.sendMessage("§dWARNING: Could be a really long list tho.");
    }

    void info(Player p) {
        p.sendMessage("§dInfo display for Enchantments v.§f" + plugin.getDescription().getVersion());
        p.sendMessage("§7                   --------------------           ");
        p.sendMessage("§6Author(s): §f" + plugin.getDescription().getAuthors());
        p.sendMessage("§7                   --------------------           ");
    }
}
