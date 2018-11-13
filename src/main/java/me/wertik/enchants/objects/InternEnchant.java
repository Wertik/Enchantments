package me.wertik.enchants.objects;

import org.bukkit.inventory.ItemStack;

public class InternEnchant {

    private int level;
    private Enchantment enchant;
    private ItemStack item;

    public InternEnchant(ItemStack item, Enchantment enchant, int level) {
        this.level = level;
        this.enchant = enchant;
        this.item = item;
    }

    public int getLevel() {
        return level;
    }

    public Enchantment getEnchant() {
        return enchant;
    }

    public ItemStack getItem() {
        return item;
    }
}
