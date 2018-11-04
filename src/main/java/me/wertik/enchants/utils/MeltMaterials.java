package me.wertik.enchants.utils;

import org.bukkit.Material;

public enum MeltMaterials {
    GOLD(Material.GOLD_ORE, Material.GOLD_INGOT),
    IRON(Material.IRON_ORE, Material.IRON_INGOT),
    STONE(Material.STONE, Material.STONE),
    ;

    private Material melted;
    private Material block;

    MeltMaterials(Material block, Material melted) {
        this.melted = melted;
        this.block = block;
    }

    public static MeltMaterials[] getMeltMaterials() {
        MeltMaterials[] output = {STONE, IRON, GOLD};
        return output;
    }

    public Material getMelted() {
        return melted;
    }

    public Material getBlock() {
        return block;
    }
}
