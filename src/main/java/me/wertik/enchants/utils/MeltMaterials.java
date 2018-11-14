package me.wertik.enchants.utils;

import org.bukkit.Material;

public enum MeltMaterials {
    GOLD(Material.GOLD_ORE, Material.GOLD_INGOT),
    IRON(Material.IRON_ORE, Material.IRON_INGOT),
    STONE(Material.STONE, Material.STONE),
    ;

    private Material result;
    private Material input;

    MeltMaterials(Material input, Material result) {
        this.result = result;
        this.input = input;
    }

    public static MeltMaterials[] getMeltMaterials() {
        MeltMaterials[] output = {STONE, IRON, GOLD};
        return output;
    }

    public Material getResult() {
        return result;
    }

    public Material getInput() {
        return input;
    }
}
