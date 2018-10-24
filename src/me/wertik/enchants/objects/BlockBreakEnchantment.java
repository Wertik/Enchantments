package me.wertik.enchants.objects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BlockBreakEnchantment extends Enchantment {

    public abstract void onBlockBreak(Player p, Block b);
}
