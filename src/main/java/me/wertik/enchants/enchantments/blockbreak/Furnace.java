package me.wertik.enchants.enchantments.blockbreak;

import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.utils.Utils;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Furnace extends Enchantment {

    private Utils utils;

    public Furnace() {
        utils = Main.getInstance().getUtils();
    }

    public String name() {
        return "furnace";
    }

    @Override
    public boolean isToolEnchant() {
        return true;
    }

    @Override
    public boolean isTokenEnchant() {
        return false;
    }

    @Override
    public boolean isArmorEnchant() {
        return false;
    }

    @Override
    public void onToolBlockBreak(BlockBreakEvent e, int level) {

        if (utils.getSmelt((e.getBlock().getType())) != null) {

            e.setDropItems(false);

            ItemStack newDrop = new ItemStack(utils.getSmelt((e.getBlock().getType())));

            List<ItemStack> drops = new ArrayList<>(e.getBlock().getDrops());

            drops.add(newDrop);

            for (ItemStack drop : drops) {
                e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);
            }
        }
    }
}
