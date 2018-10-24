package me.wertik.enchants.handlers;

import me.wertik.enchants.Main;
import me.wertik.enchants.enchantments.FurnaceEnchantment;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantManager {

    private Main plugin;
    private List<Enchantment> enchantments;

    public EnchantManager() {
        enchantments = new ArrayList<Enchantment>();
        plugin = Main.getInstance();
    }

    public void loadEnchantments() {
        enchantments.add(new FurnaceEnchantment());
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public Enchantment getEnchantByLoreLine(String line, String type) {

        for (Enchantment enchant : enchantments) {
            if (enchant.lore().equals(line) && enchant.type().equals(type))
                return enchant;
        }

        return null;
    }

    public boolean isEnchanted(ItemStack item) {

        if (item.getType().equals(Material.AIR) || !item.hasItemMeta())
            return false;
        if (!item.getItemMeta().hasLore())
            return false;

        for (Enchantment enchant : enchantments) {
            if (getLoreLine(item.getItemMeta().getLore()).equals(enchant.lore()))
                return true;
        }
        return false;
    }

    public boolean isEnchantable(ItemStack item) {
        return false;
    }

    public String getLoreLine(List<String> lore) {
        // Config option: check whole lore or the zeroth line only
        return lore.get(0);
    }

    public ItemStack enchantItem(ItemStack item, Enchantment enchant) {

        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();

        lore.add(enchant.lore());

        if (itemMeta.hasLore()) {
            lore.addAll(itemMeta.getLore());
        }

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }
}
