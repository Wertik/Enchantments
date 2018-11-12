package me.wertik.enchants.handlers;

import com.sun.istack.internal.NotNull;
import me.mrwener.enchants.nbt.NBTEditor;
import me.mrwener.enchants.nbt.NBTUtils;
import me.wertik.enchants.ConfigLoader;
import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EnchantManager {

    private List<Enchantment> enchantments;
    private ConfigLoader configLoader;

    public EnchantManager() {
        enchantments = new ArrayList<>();
        configLoader = Main.getInstance().getConfigLoader();
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
     * value: ""[enchant1, enchant2]""
     *
     * enchant1.level = x
     * enchant2.level = y
     *
     * key: "levels"
     * value: ""[x, y]""
     *
     * */

    public HashMap<Enchantment, Integer> getEnchantsOnItem(ItemStack item) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        // Enchant names

        List<Enchantment> enchantsList = new ArrayList<>();

        String NBTData = NBTUtils.strip(NBTEditor.getNBT(item, "enchants"));

        NBTData = NBTData.replace("[", "");

        NBTData = NBTData.replace("]", "");

        String[] NBTDataList = NBTData.split(",");

        List<String> enchantNamesList = new ArrayList<>(Arrays.asList(NBTDataList));

        for (String enchantName : enchantNamesList) {
            enchantsList.add(getEnchantByName(enchantName.trim()));
        }

        // Enchant levels

        List<Integer> levelsList = new ArrayList<>();

        NBTData = NBTUtils.strip(NBTEditor.getNBT(item, "levels"));

        NBTData = NBTData.replace("[", "");

        NBTData = NBTData.replace("]", "");

        NBTDataList = NBTData.split(",");

        List<String> levelStringList = new ArrayList<>(Arrays.asList(NBTDataList));

        for (String levelString : levelStringList) {
            levelsList.add(Integer.valueOf(levelString.trim()));
        }

        // Complete

        for (int i = 0; i < enchantsList.size(); i++) {
            enchants.put(enchantsList.get(0), levelsList.get(0));
        }

        return enchants;
    }

    private List<String> getEnchantsOnItemInString(ItemStack item) {

        List<String> enchants = new ArrayList<>();

        for (Enchantment enchant : getEnchantsOnItem(item).keySet()) {
            enchants.add(enchant.name());
        }

        return enchants;
    }

    // Remove enchant

    public ItemStack removeEnchant(ItemStack item, Enchantment enchant) {

        // NBT

        List<Enchantment> enchants = new ArrayList<>(getEnchantsOnItem(item).keySet());

        List<Integer> levels = new ArrayList<>(getEnchantsOnItem(item).values());

        for (int i = 0; i < enchants.size(); i++) {
            Enchantment enchant1 = enchants.get(i);

            if (enchant.equals(enchant1)) {
                enchants.remove(enchant1);
                levels.remove(i);
            }
        }

        item = NBTEditor.writeNBT(item, "enchants", enchants.toString());

        item = NBTEditor.writeNBT(item, "levels", levels.toString());

        // Lore

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        for (String line : lore) {
            if (line.equals(enchant.line())) {
                lore.remove(line);
                break;
            }
        }

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack clearEnchants(ItemStack item) {

        List<Enchantment> enchants = new ArrayList<>(getEnchantsOnItem(item).keySet());

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        for (Enchantment enchant : enchants) {
            lore.remove(enchant.line());
        }

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        item = NBTEditor.removeNBT(item, "key");

        return NBTEditor.removeNBT(item, "enchants");
    }

    // Is item enchanted?

    public boolean isEnchanted(ItemStack item) {
        if (item == null)
            return false;
        if (item.getType().equals(Material.AIR) || !item.hasItemMeta())
            return false;
        if (!item.getItemMeta().hasLore())
            return false;
        return NBTEditor.hasNBTTag(item, "enchants");
    }

    /**
     * Checks if {@link @item} is enchantable with {@link @enchant}
     *
     * @param enchant Enchant to check for.
     * @param item    Item to check on.
     * @return True if enchantable.
     */

    public boolean isEnchantable(Enchantment enchant, ItemStack item) {
        Bukkit.broadcastMessage(enchant.enchantableItemTypes().toString());
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
    public ItemStack enchantItem(@NotNull ItemStack item, @NotNull Enchantment enchant, int level) {

        if (!isEnchanted(item)) {

            // Add NBT

            item = NBTEditor.writeNBT(item, "enchants", enchant.name());

            item = NBTEditor.writeNBT(item, "levels", String.valueOf(level));

            // Add lore

            ItemMeta itemMeta = item.getItemMeta();

            List<String> newLore = new ArrayList<>();

            newLore.add(configLoader.parse(enchant.line(), enchant, level));

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

            List<Integer> levelsToApply = new ArrayList<>(getEnchantsOnItem(item).values());

            levelsToApply.add(level);

            item = NBTEditor.writeNBT(item, "levels", levelsToApply.toString());

            // Add lore

            ItemMeta itemMeta = item.getItemMeta();

            List<String> newLore = new ArrayList<>();

            newLore.add(configLoader.parse(enchant.line(), enchant, level));

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
