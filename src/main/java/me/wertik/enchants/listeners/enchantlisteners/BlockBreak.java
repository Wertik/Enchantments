package main.java.me.wertik.enchants.listeners.enchantlisteners;

import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.handlers.EnchantManager;
import main.java.me.wertik.enchants.objects.Enchantment;
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
            Enchantment enchant = enchantManager.getEnchantByLoreLine(enchantManager.getLoreLine(tool.getItemMeta().getLore()));
            enchant.onBlockBreak(e);
        }
    }
}
