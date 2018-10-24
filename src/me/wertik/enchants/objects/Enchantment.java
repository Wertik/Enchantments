package me.wertik.enchants.objects;

import java.util.List;

public abstract class Enchantment {

    public abstract String lore();

    public abstract List<String> enchantableItemTypes();

    public abstract List<String> workableBiomeTypes();

    public abstract List<String> workableRegionNames();

    public abstract String displayName();

    public abstract String name();

    public abstract String type();
}
