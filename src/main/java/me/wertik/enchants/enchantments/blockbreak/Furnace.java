package me.wertik.enchants.enchantments.blockbreak;

import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.utils.Utils;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
    public void onBlockBreak(BlockBreakEvent e, int level) {

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

    @Override
    public boolean usesBlockBreak() {
        return true;
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent e, int level) {
    }

    @Override
    public boolean usesEntityDamage() {
        return false;
    }
}
