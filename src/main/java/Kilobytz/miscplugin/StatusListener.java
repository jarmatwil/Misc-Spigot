package Kilobytz.miscplugin;

import java.lang.*;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
//import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public class StatusListener implements Listener {

    StatusHandler sH;

    void setStatus(StatusHandler sH) {
        this.sH = sH;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent dmg) {
        if (dmg.getEntity() instanceof Player) {
            Player dmgedPlayer = (Player) dmg.getEntity();
            double rng = Math.ceil(Math.random() * 100);
            if (rng <= 100) {
                dmgedPlayer.sendMessage("rng number was successful and was: " + rng);
                sH.setMangled(dmgedPlayer, true);
            } else {
                dmgedPlayer.sendMessage("rng number failed and was: " + rng);
            }
        }
    }
}


//call decimal based on smite level, then calulate math using decimal variable and after total calculations, add mangle reduc