package me.wertik.enchants.listeners.enchantlisteners;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.EnchantManager;
import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BlockBreak implements Listener {

    private Main plugin;
    private EnchantManager enchantManager;
    private Utils utils;

    public BlockBreak() {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
        utils = plugin.getUtils();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();

        if (enchantManager.isEnchanted(tool)) {
            HashMap<Enchantment, Integer> enchants = enchantManager.getEnchantsOnItem(tool);

            for (Enchantment enchant : enchants.keySet()) {
                if (utils.decide(enchant, enchants.get(enchant)))
                    enchant.onBlockBreak(e, enchants.get(enchant));
            }
        }
    }
}
