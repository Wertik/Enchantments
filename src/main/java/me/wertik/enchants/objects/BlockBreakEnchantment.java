package main.java.me.wertik.enchants.objects;

import org.bukkit.event.block.BlockBreakEvent;

public abstract class BlockBreakEnchantment extends Enchantment {

    public abstract void onBlockBreak(BlockBreakEvent e);
}
