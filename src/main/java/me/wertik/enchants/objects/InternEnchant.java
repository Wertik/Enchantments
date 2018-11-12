package me.wertik.enchants.objects;

public class InternEnchant {

    private int level;
    private Enchantment enchant;

    public InternEnchant(Enchantment enchant, int level) {
        this.level = level;
        this.enchant = enchant;
    }

    public int getLevel() {
        return level;
    }

    public Enchantment getEnchant() {
        return enchant;
    }
}
