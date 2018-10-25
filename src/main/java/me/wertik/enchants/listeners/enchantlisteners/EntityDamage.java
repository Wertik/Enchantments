package main.java.me.wertik.enchants.listeners.enchantlisteners;

import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.handlers.EnchantManager;
import main.java.me.wertik.enchants.objects.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamage implements Listener {

    private Main plugin;
    private EnchantManager enchantManager;

    public EntityDamage() {
        plugin = Main.getInstance();
        enchantManager = plugin.getEnchantManager();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {
            Enchantment enchant = enchantManager.getEnchantByLoreLine(enchantManager.getLoreLine(((Player) e.getDamager()).getInventory().getItemInMainHand().getItemMeta().getLore()));
            enchant.onDamage(e);
        }
    }
}
