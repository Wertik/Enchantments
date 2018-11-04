package me.wertik.enchants.handlers;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.wertik.enchants.Main;
import me.wertik.enchants.objects.Enchantment;
import me.wertik.enchants.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

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

    public void processEvent(Event e, Player p) {

        // Tools

        ItemStack tool = p.getInventory().getItemInMainHand();

        if (enchantManager.isEnchanted(tool)) {
            HashMap<Enchantment, Integer> enchants = enchantManager.getEnchantsOnItem(tool);

            for (Enchantment enchant : enchants.keySet()) {

                // regionNames

                if (enchant.workableRegionNames() != null)
                    if (!enchant.workableRegionNames().isEmpty())
                        if (worldGuard != null)
                            if (!checkRegion(p, enchant))
                                return;

                // Biome types

                if (enchant.workableBiomeTypes() != null)
                    if (!enchant.workableBiomeTypes().isEmpty())
                        if (!enchant.workableBiomeTypes().contains(p.getLocation().getBlock().getBiome().name()))
                            return;

                if (utils.decide(enchant.chance(), enchants.get(enchant))) {

                    if (enchant.usesBlockBreak())
                        enchant.onBlockBreak((BlockBreakEvent) e, enchants.get(enchant));
                    if (enchant.usesEntityDamage())
                        enchant.onDamage((EntityDamageByEntityEvent) e, enchants.get(enchant));
                }
            }
        }
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
