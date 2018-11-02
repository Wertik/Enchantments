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
                "§6/enchants clear §7== §6clear all enchantments from you hand");
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

        for (Enchantment enchant : enchantManager.getEnchantments()) {
            p.sendMessage("§6" + enchant.name());
            p.sendMessage(" §6Display name: " + enchant.displayName());
            p.sendMessage(" §6Enchant lore line: " + enchant.line());

            p.sendMessage(" §6Description:");

            for (String line : enchant.description()) {
                p.sendMessage(line);
            }

            p.sendMessage(" §6Enchantable item types:");
            for (String line : enchant.enchantableItemTypes()) {
                p.sendMessage("  §8- §f" + line);
            }

            p.sendMessage(" §6Workable biome types:");
            for (String line : enchant.workableBiomeTypes()) {
                p.sendMessage("  §8- §f" + line);
            }

            p.sendMessage(" §6Workable regions:");
            for (String line : enchant.workableRegionNames()) {
                p.sendMessage("  §8- §f" + line);
            }
        }
    }
}
