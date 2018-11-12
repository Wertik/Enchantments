package me.wertik.enchants.handlers;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.objects.InternEnchant;
import me.wertik.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnchantEventHandler {

    private Main plugin;
    private Utils utils;
    private EnchantManager enchantManager;
    private WorldGuardPlugin worldGuard;

    public EnchantEventHandler() {
        plugin = Main.getInstance();
        utils = plugin.getUtils();
        enchantManager = plugin.getEnchantManager();
        worldGuard = plugin.getWorldGuard();
    }

    public void processEvent(BlockBreakEvent e, Player p) {

        // Tool
        InternEnchant enchantTool = isGo(p.getItemInHand(), p);

        if (enchantTool != null)
            enchantTool.getEnchant().onToolBlockBreak(e, enchantTool.getLevel());

        // Token
        for (InternEnchant enchantToken : isGoToken(p)) {
            enchantToken.getEnchant().onToolBlockBreak(e, enchantToken.getLevel());
        }

        // Armor
        for (InternEnchant enchantArmor : isGoArmor(p)) {
            enchantArmor.getEnchant().onArmorBlockBreak(e, enchantArmor.getLevel());
        }
    }

    public void processEvent(EntityDamageByEntityEvent e, Player p) {

        // Tool
        InternEnchant enchantTool = isGo(p.getItemInHand(), p);

        if (enchantTool != null)
            enchantTool.getEnchant().onToolDamage(e, enchantTool.getLevel());

        // Token
        for (InternEnchant enchantToken : isGoToken(p)) {
            enchantToken.getEnchant().onTokenDamage(e, enchantToken.getLevel());
        }

        // Armor
        for (InternEnchant enchantArmor : isGoArmor(p)) {
            enchantArmor.getEnchant().onArmorDamage(e, enchantArmor.getLevel());
        }
    }

    public void processEvent(PlayerMoveEvent e, Player p) {

        // Tool
        InternEnchant enchantTool = isGo(p.getItemInHand(), p);

        if (enchantTool != null)
            enchantTool.getEnchant().onToolMove(e, enchantTool.getLevel());

        // Token
        for (InternEnchant enchantToken : isGoToken(p)) {
            enchantToken.getEnchant().onTokenMove(e, enchantToken.getLevel());
        }

        // Armor
        for (InternEnchant enchantArmor : isGoArmor(p)) {
            enchantArmor.getEnchant().onArmorMove(e, enchantArmor.getLevel());
        }
    }

    public void processEvent(PlayerInteractEvent e, Player p) {

        // Tool
        InternEnchant enchantTool = isGo(p.getItemInHand(), p);

        if (enchantTool != null)
            enchantTool.getEnchant().onToolInteract(e, enchantTool.getLevel());

        // Token
        for (InternEnchant enchantToken : isGoToken(p)) {
            enchantToken.getEnchant().onTokenInteract(e, enchantToken.getLevel());
        }

        // Armor
        for (InternEnchant enchantArmor : isGoArmor(p)) {
            enchantArmor.getEnchant().onArmorInteract(e, enchantArmor.getLevel());
        }
    }

    public List<InternEnchant> isGoArmor(Player p) {
        Inventory inv = p.getInventory();

        ItemStack[] contents = {((PlayerInventory) inv).getHelmet(), ((PlayerInventory) inv).getChestplate(), ((PlayerInventory) inv).getLeggings(), ((PlayerInventory) inv).getBoots()};

        List<InternEnchant> enchants = new ArrayList<>();

        for (ItemStack item : contents) {
            InternEnchant enchant = isGo(item, p);

            if (enchant != null)
                enchants.add(enchant);
            else
                continue;
        }

        return enchants;
    }

    public InternEnchant isGo(ItemStack item, Player p) {

        if (enchantManager.isEnchanted(item)) {
            HashMap<Enchantment, Integer> enchants = enchantManager.getEnchantsOnItem(item);

            List<InternEnchant> internEnchants = new ArrayList<>();

            for (Enchantment enchant1 : enchants.keySet()) {
                internEnchants.add(new InternEnchant(enchant1, enchants.get(enchant1)));
            }

            for (InternEnchant internEnchant : internEnchants) {

                Enchantment enchant = internEnchant.getEnchant();

                // regionNames

                if (enchant.workableRegionNames() != null)
                    if (!enchant.workableRegionNames().isEmpty())
                        if (worldGuard != null)
                            if (!checkRegion(p, enchant))
                                return null;

                // Biome types

                if (enchant.workableBiomeTypes() != null)
                    if (!enchant.workableBiomeTypes().isEmpty())
                        if (!enchant.workableBiomeTypes().contains(p.getLocation().getBlock().getBiome().name()))
                            return null;

                if (utils.decide(enchant.chance(), enchants.get(enchant))) {
                    // Process equivalent event
                    return internEnchant;
                }
            }
        }
        return null;
    }

    public List<InternEnchant> isGoToken(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54);

        inv.setContents(p.getInventory().getContents());

        inv.setItem(36, null);
        inv.setItem(37, null);
        inv.setItem(38, null);
        inv.setItem(39, null);

        inv.setItem(p.getInventory().getHeldItemSlot(), null);

        ItemStack[] contents = inv.getContents();

        List<InternEnchant> enchants = new ArrayList<>();

        for (ItemStack item : contents) {
            InternEnchant enchant = isGo(item, p);

            if (enchant != null)
                enchants.add(enchant);
            else
                continue;
        }

        return enchants;
    }

    private boolean checkRegion(Player p, Enchantment enchant) {

        LocalPlayer localPlayer = worldGuard.wrapPlayer(p);
        Vector vector = localPlayer.getPosition();
        List<String> regionSet = worldGuard.getRegionManager(p.getWorld()).getApplicableRegionsIDs(vector);

        for (String region : regionSet) {
            if (enchant.workableRegionNames().contains(region))
                return true;
        }

        return false;
    }
}
