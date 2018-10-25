package main.java.me.wertik.enchants.enchantments.entitydamage;

import main.java.me.wertik.enchants.Main;
import main.java.me.wertik.enchants.objects.EntityDamageEnchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ThorStrike extends EntityDamageEnchantment {

    private Main plugin;

    public ThorStrike() {
        plugin = Main.getInstance();
    }

    public String name() {
        return "thorstrike";
    }

    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {

            Entity entity = e.getEntity();
            Damageable damageable = (Damageable) entity;
            Player player = (Player) e.getDamager();

            player.getWorld().strikeLightningEffect(entity.getLocation());
            damageable.damage(10);
        }
    }

    public String type() {
        return "entityDamage";
    }
}
