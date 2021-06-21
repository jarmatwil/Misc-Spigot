package Kilobytz.miscplugin;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EffectListener implements Listener {

    StatusHandler sH;
    Crafting cR;

    void setStatus(StatusHandler sH) {
        this.sH = sH;
    }

    void loadItems(Crafting cR) { this.cR = cR; }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent heal) {
        if(!(heal.getEntity() instanceof Player)) {
            return;
            }
        EntityRegainHealthEvent.RegainReason healReason = heal.getRegainReason();
        Player healPlayer = (Player) heal.getEntity();
        EntityRegainHealthEvent.RegainReason food = EntityRegainHealthEvent.RegainReason.SATIATED;
        if(healReason.equals(food)) {
            boolean checkMangled = sH.checkMangledPlayer(healPlayer);
            if(checkMangled) {
                heal.setAmount(0.0);
                heal.setCancelled(true);
            }
        }

        //WORKS, ADD IN ABILITY TO TURN SATIATED OFF WHEN MANGLED ON;

    }
    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if(event.getEntity().getKiller() instanceof Player) {
            Player killer = event.getEntity().getKiller();
            ItemStack item = killer.getInventory().getItemInMainHand();
            boolean isSmite = checkSmite(item);
            if(isSmite) {
                int smiteLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                killer.sendMessage("smite lvl is " + smiteLevel);
                double hp = killer.getHealth();
                double hpLost = killer.getHealthScale()-hp;
                switch(smiteLevel) {
                    case 1 :
                        double mathRaw1 = hpLost*0.1;
                        double smite1Regen = Math.round(mathRaw1);
                        killer.setHealth(hp+smite1Regen);
                        return;
                    case 2 :
                        double mathRaw2 = hpLost*0.2;
                        double smite2Regen = Math.round(mathRaw2);
                        killer.setHealth(hp+smite2Regen);
                        return;
                    case 3 :
                        double mathRaw3 = hpLost*0.3;
                        double smite3Regen = Math.round(mathRaw3);
                        killer.setHealth(hp+smite3Regen);
                        return;
                    case 4 :
                        double mathRaw4 = hpLost*0.4;
                        double smite4Regen = Math.round(mathRaw4);
                        killer.setHealth(hp+smite4Regen);
                        return;
                    default:
                        double mathRaw5 = hpLost*0.5;
                        double smite5Regen = Math.round(mathRaw5);
                        killer.setHealth(hp+smite5Regen);

                }
            }
        }
    }
    @EventHandler
    public void healMangle(PlayerInteractEvent event) {
        Player interactPlayer = event.getPlayer();
        ItemStack mainHand = interactPlayer.getInventory().getItemInMainHand();
        if(mainHand.getType() == Material.INK_SACK && mainHand.getDurability() == 13 && mainHand.hasItemMeta()) {
            if (sH.checkMangledPlayer(interactPlayer)) {
                sH.setMangled(interactPlayer, false);
                interactPlayer.getInventory().getItemInMainHand().setAmount(interactPlayer.getInventory().getItemInMainHand().getAmount() - 1);
            }
        }
    }

    public boolean checkSmite(ItemStack item) {
        return item.containsEnchantment(Enchantment.DAMAGE_UNDEAD);
    }
}
