package Kilobytz.miscplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class StatusTimer {

    Main main;

    Map<UUID, Integer> timerShit = new HashMap<>();

    public StatusTimer(Main main) { this.main = main; }

    public void timerStart(Player player) {
        player.sendMessage("status start");
        UUID pUUID = player.getUniqueId();
        timerShit.put(pUUID, Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {

            @Override
            public void run() {
                player.sendMessage("status effect goes here");
            }
        },0, 100L));
    }

    public void timerEnd(Player player) {
        UUID pUUID = player.getUniqueId();
        if(timerShit.containsKey(pUUID)) {
            int task = timerShit.get(pUUID);
            Bukkit.getServer().getScheduler().cancelTask(task);
            player.sendMessage("status over");
        }
    }

}
