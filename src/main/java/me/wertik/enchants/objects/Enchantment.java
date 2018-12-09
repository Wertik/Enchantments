package me.wertik.enchants.objects;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.DataHandler;
import me.wertik.enchants.handlers.EnchantManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public abstract class Enchantment {

    private Main plugin;
    private DataHandler dataHandler;
    private EnchantManager enchantManager;

    public Enchantment() {
        plugin = Main.getInstance();
        dataHandler = plugin.getDataHandler();
        enchantManager = plugin.getEnchantManager();
    }

    public abstract String name();

    public String displayName() {
        return dataHandler.getDisplayName(name());
    }

    public String line() {
        return dataHandler.getLoreLine(name());
    }

    public List<String> description() {
        return dataHandler.getDescription(name());
    }

    public int maxLevel() {
        return dataHandler.getMaxLevel(name());
    }

    public double chance() {
        return dataHandler.getChance(name());
    }

    // Has to be in main hand
    public abstract boolean isToolEnchant();

    // In inventory
    public abstract boolean isTokenEnchant();

    // Equipped in armor slot
    public abstract boolean isArmorEnchant();

    // Conditions

    public List<String> enchantableItemTypes() {
        return dataHandler.getEnchantableItemTypes(name());
    }

    public List<String> workableBiomeTypes() {
        return dataHandler.getWorkableBiomeTypes(name());
    }

    public List<String> workableRegionNames() {
        return dataHandler.getWorkableRegionNames(name());
    }

    public List<String> workableWorldNames() {
        return dataHandler.getWorkableWorldNames(name());
    }

    // Event usage

    // Tool

    public void onToolBlockBreak(BlockBreakEvent e, int level) {
        return;
    }

    public void onToolDamage(EntityDamageByEntityEvent e, int level) {
        return;
    }

    public void onToolDamageIncome(EntityDamageEvent e, int level) {
        return;
    }

    public void onToolMove(PlayerMoveEvent e, int level) {
        return;
    }

    public void onToolInteract(PlayerInteractEvent e, int level) {
        return;
    }

    public void onToolDeath(PlayerDeathEvent e, int level) {
        return;
    }

    // Token

    public void onTokenBlockBreak(BlockBreakEvent e, int level) {
        return;
    }

    public void onTokenDamage(EntityDamageByEntityEvent e, int level) {
        return;
    }

    public void onTokenDamageIncome(EntityDamageEvent e, int level) {
        return;
    }

    public void onTokenMove(PlayerMoveEvent e, int level) {
        return;
    }

    public void onTokenInteract(PlayerInteractEvent e, int level) {
        return;
    }

    public void onTokenDeath(PlayerDeathEvent e, int level) {
        return;
    }

    // Armor

    public void onArmorBlockBreak(BlockBreakEvent e, int level) {
        return;
    }

    public void onArmorDamage(EntityDamageByEntityEvent e, int level) {
        return;
    }

    public void onArmorDamageIncome(EntityDamageEvent e, int level) {
        return;
    }

    public void onArmorMove(PlayerMoveEvent e, int level) {
        return;
    }

    public void onArmorInteract(PlayerInteractEvent e, int level) {
        return;
    }

    public void onArmorDeath(PlayerDeathEvent e, int level) {
        return;
    }

    //

    public void hook() {
        enchantManager.hookEnchant(this);
    }

    public void consumeToken(Player p) {
        Inventory newInventory = Bukkit.createInventory(null, 54);
        newInventory.setItem(p.getInventory().getHeldItemSlot(), null);

        Inventory inv = p.getInventory();

        // Find the item...
        for (ItemStack item : inv) {
            if (enchantManager.hasEnchantment(item, this)) {
                if (item.getAmount() > 1)
                    item.setAmount(item.getAmount() - 1);
                else
                    inv.remove(item);
                break;
            }
        }
    }

    public void consumeTool(Player p) {
        if (p.getItemInHand().getAmount() > 1) {
            ItemStack newTool = p.getItemInHand();
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
            p.setItemInHand(newTool);
        } else
            p.setItemInHand(null);
    }

    public void consumeArmor(Player p) {
        Inventory inv = p.getInventory();
        ItemStack[] contents = {((PlayerInventory) inv).getHelmet(), ((PlayerInventory) inv).getChestplate(), ((PlayerInventory) inv).getLeggings(), ((PlayerInventory) inv).getBoots()};

        // Find the item...
        for (ItemStack item : contents) {
            if (enchantManager.hasEnchantment(item, this)) {
                inv.remove(item);
                break;
            }
        }
    }
}
