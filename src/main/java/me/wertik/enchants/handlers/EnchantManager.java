package me.wertik.enchants.handlers;

import me.MrWener.Enchants.nbt.ItemNBTEditor;
import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
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

    // Not sure how to make this easier, need the list of available enchants.

    public void hookEnchant(Enchantment enchant) {
        enchantments.add(enchant);
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public List<String> getEnchantNames() {
        List<String> names = new ArrayList<>();

        for (Enchantment enchant : enchantments) {
            names.add(enchant.name());
        }

        return names;
    }

    public Enchantment getEnchantByLoreLine(String line) {

        for (Enchantment enchant : enchantments) {
            if (enchant.line().equals(line))
                return enchant;
        }
        return null;
    }

    public Enchantment getEnchantByName(String name) {
        for (Enchantment enchant : enchantments) {
            if (enchant.name().equals(name))
                return enchant;
        }
        return null;
    }

    // Return enchants on an item
    // TO-DO: rewrite for more enchants support

    public List<Enchantment> getEnchantsOnItem(ItemStack item) {
        List<Enchantment> enchants = new ArrayList<>();

        enchants.add(getEnchantByLoreLine(getLoreLine(item.getItemMeta().getLore())));

        return enchants;
    }

    // Remove enchant
    // Rewrite for NBT

    public ItemStack removeEnchant(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        lore.remove(0);

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    // Rewrite for NBT, just a tryout

    public ItemStack clearEnchants(ItemStack item) {
        return removeEnchant(item);
    }

    // Is item enchanted?

    public boolean isEnchanted(ItemStack item) {

        if (item.getType().equals(Material.AIR) || !item.hasItemMeta())
            return false;
        if (!item.getItemMeta().hasLore())
            return false;

        for (Enchantment enchant : enchantments) {
            if (getLoreLine(item.getItemMeta().getLore()).equals(enchant.line()))
                return true;
        }
        return false;
    }

    // Is it enchantable?

    public boolean isEnchantable(Enchantment enchant, ItemStack item) {
        return enchant.enchantableItemTypes().contains(item.getType().toString());
    }

    // Return line from lore, where the enchant is written.
    // TO-DO: replace by NBT data

    public String getLoreLine(List<String> lore) {
        // Config option: check whole lore or the zeroth line only
        return lore.get(0);
    }

    public ItemStack enchantItem(ItemStack item, Enchantment enchant) {

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add(enchant.line());

        if (itemMeta.hasLore()) {
            lore.addAll(itemMeta.getLore());
        }

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        // Todo Call method that will write NBTTag to item!
        item = ItemNBTEditor.writeNBT(item, "data", enchant.name());

        return item;
    }
}
