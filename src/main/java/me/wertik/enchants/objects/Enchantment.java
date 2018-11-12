package me.wertik.enchants.objects;

import me.wertik.enchants.Main;
import me.wertik.enchants.handlers.DataHandler;
import me.wertik.enchants.handlers.EnchantManager;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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

    // Event usage

    // Tool

    public void onToolBlockBreak(BlockBreakEvent e, int level) {
        return;
    }

    public void onToolDamage(EntityDamageByEntityEvent e, int level) {
        return;
    }

    public void onToolMove(PlayerMoveEvent e, int level) {
        return;
    }

    public void onToolInteract(PlayerInteractEvent e, int level) {
        return;
    }

    // Token

    public void onTokenBlockBreak(BlockBreakEvent e, int level) {
        return;
    }

    public void onTokenDamage(EntityDamageByEntityEvent e, int level) {
        return;
    }

    public void onTokenMove(PlayerMoveEvent e, int level) {
        return;
    }

    public void onTokenInteract(PlayerInteractEvent e, int level) {
        return;
    }

    // Armor

    public void onArmorBlockBreak(BlockBreakEvent e, int level) {
        return;
    }

    public void onArmorDamage(EntityDamageByEntityEvent e, int level) {
        return;
    }

    public void onArmorMove(PlayerMoveEvent e, int level) {
        return;
    }

    public void onArmorInteract(PlayerInteractEvent e, int level) {
        return;
    }

    //

    public void hook() {
        enchantManager.hookEnchant(this);
    }
}
