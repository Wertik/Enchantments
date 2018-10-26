package me.wertik.enchants.handlers;

import com.sun.istack.internal.NotNull;
import me.MrWener.Enchants.nbt.NBTEditor;
import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
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

    /*
     * NBT data info:
     *
     * key: "enchants"
     * value: "[enchant1, enchant2]"
     *
     * */

    public List<Enchantment> getEnchantsOnItem(ItemStack item) {
        List<Enchantment> enchants = new ArrayList<>();

        String NBTData = NBTEditor.getNBT(item, "enchants");

        NBTData = NBTData.replace("[", "");

        NBTData = NBTData.replace("]", "");

        String[] NBTDataList = NBTData.split(",");

        List<String> enchantNames = new ArrayList<>(Arrays.asList(NBTDataList));

        for (String enchantName : enchantNames) {
            enchants.add(getEnchantByName(enchantName));
        }

        return enchants;
    }

    public List<String> getEnchantsOnItemInString(ItemStack item) {

        String dataNBT = NBTEditor.getNBT(item, "enchants");

        dataNBT = dataNBT.replace("[", "");

        dataNBT = dataNBT.replace("]", "");

        String[] NBTDataList = dataNBT.split(",");

        List<String> enchantNames = new ArrayList<>(Arrays.asList(NBTDataList));

        return enchantNames;

    }

    // Remove enchant

    public ItemStack removeEnchant(ItemStack item, Enchantment enchant) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();

        lore.remove(0);

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    // Rewrite for NBT, just a tryout

    public ItemStack clearEnchants(ItemStack item) {
        //return removeEnchant(item);
        return null;
    }

    // Is item enchanted?

    public boolean isEnchanted(ItemStack item) {

        if (item.getType().equals(Material.AIR) || !item.hasItemMeta())
            return false;
        if (!item.getItemMeta().hasLore())
            return false;
        return NBTEditor.hasNBT(item);
    }

    // Is it enchantable?

    public boolean isEnchantable(Enchantment enchant, ItemStack item) {
        return enchant.enchantableItemTypes().contains(item.getType().toString());
    }

    /**
     * Enchants {@link @item} with {@link @enchant}.
     *
     * @param item    Item to enchant.. should be enchantable.
     * @param enchant Enchant to put on the item.
     * @return Return enchanted item, As new.
     */

    // Todo: config option: apply lore?
    public ItemStack enchantItem(@NotNull ItemStack item, @NotNull Enchantment enchant) {

        if (!isEnchanted(item)) {

            // Add NBT

            item = NBTEditor.writeNBT(item, "enchants", enchant.name());

            // Add lore

            ItemMeta itemMeta = item.getItemMeta();

            List<String> newLore = new ArrayList<>();

            newLore.add(enchant.line());

            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                newLore.addAll(lore);
            }

            itemMeta.setLore(newLore);
            item.setItemMeta(itemMeta);

        } else {

            // Add NBT

            List<String> enchantsToApply = getEnchantsOnItemInString(item);

            enchantsToApply.add(enchant.name());

            item = NBTEditor.writeNBT(item, "enchants", enchantsToApply.toString());

            // Add lore

            ItemMeta itemMeta = item.getItemMeta();

            List<String> newLore = new ArrayList<>();

            newLore.add(enchant.line());

            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                newLore.addAll(lore);
            }

            itemMeta.setLore(newLore);
            item.setItemMeta(itemMeta);
        }

        return item;
    }
}
