package me.wertik.enchants.listeners;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.EnchantManager;
import me.wertik.enchants.objects.BlockBreakEnchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    private Main plugin;
    private EnchantManager enchantManager;

    public BlockBreak() {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();

        if (enchantManager.isEnchanted(tool)) {

            BlockBreakEnchantment enchant = (BlockBreakEnchantment) enchantManager.getEnchantByLoreLine(enchantManager.getLoreLine(tool.getItemMeta().getLore()), "blockBreak");
            enchant.onBlockBreak(e.getPlayer(), e.getBlock());
        }
    }
}
