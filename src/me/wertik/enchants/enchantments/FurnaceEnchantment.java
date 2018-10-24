package me.wertik.enchants.enchantments;

import me.wertik.enchants.objects.BlockBreakEnchantment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class FurnaceEnchantment extends BlockBreakEnchantment {

    public String lore() {
        return "§cFurnace";
    }

    public List<String> enchantableItemTypes() {
        return null;
    }

    public List<String> workableBiomeTypes() {
        return null;
    }

    public List<String> workableRegionNames() {
        return null;
    }

    public String displayName() {
        return "§cFurnace enhancement";
    }

    public String name() {
        return "furnace";
    }

    public String type() {
        return "blockBreak";
    }

    public void onBlockBreak(Player p, Block b) {
        p.sendMessage("§cFurnace enchant!");
    }
}
