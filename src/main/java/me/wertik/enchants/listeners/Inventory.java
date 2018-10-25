package me.wertik.enchants.listeners;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.BookManager;
import me.wertik.enchants.handlers.EnchantManager;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Inventory implements Listener {

    private Main plugin;
    private BookManager bookManager;
    private EnchantManager enchantManager;

    public Inventory() {
        plugin = Main.getInstance();
        bookManager = plugin.getBookManager();
        enchantManager = plugin.getEnchantManager();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null)
            return;

        if (e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {

            if (e.getClickedInventory().getItem(e.getSlot()) != null) {

                ItemStack item = e.getClickedInventory().getItem(e.getSlot());

                if (bookManager.isBook(e.getCursor())) {

                    Enchantment enchant = bookManager.getEnchantFromBook(e.getCursor());

                    if (enchantManager.isEnchantable(enchant, item) && !enchantManager.isEnchanted(item)) {

                        e.getWhoClicked().sendMessage("§3Enchanting..");
                        enchantManager.enchantItem(item, enchant);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
