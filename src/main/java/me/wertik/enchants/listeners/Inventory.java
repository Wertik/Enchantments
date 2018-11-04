package me.wertik.enchants.listeners;

import me.mrwener.enchants.nbt.NBTEditor;
import me.mrwener.enchants.nbt.NBTUtils;
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

                    ItemStack book = e.getCursor();
                    Enchantment enchant = bookManager.getEnchantFromBook(book);
                    int level = bookManager.getLevelFromBook(book);

                    if (enchantManager.isEnchantable(enchant, item)) {

                        if (bookManager.isSuccessful(Double.valueOf(NBTUtils.strip(NBTEditor.getNBT(book, "success_rate")).trim()))) {

                            e.getWhoClicked().sendMessage("§6Enchanting..");
                            e.getClickedInventory().setItem(e.getSlot(), enchantManager.enchantItem(item, enchant, level));
                            e.setCursor(null);
                            e.setCancelled(true);
                        } else {
                            e.setCursor(null);
                            e.setCancelled(true);
                            e.getWhoClicked().sendMessage("§6You failed..");
                        }
                    }
                }
            }
        }
    }
}
