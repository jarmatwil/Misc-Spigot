package Kilobytz.miscplugin;

import com.comphenix.protocol.PacketType;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoaderInit {

    Main main;
    PluginManager pluginManager;
    List<Player> playersOnline = new ArrayList<>();
    HashMap<Entity,LoaderHandling> isLoading = new HashMap<>();
    World world;


    public LoaderInit(Main main, PluginManager pluginManager) {
        this.main = main;
        this.pluginManager = pluginManager;
    }



    public void entityCheck() {
        Server server = main.getServer();
        addOnlinePlayers();
        Player player1;
        try {
            player1 = playersOnline.get(0);
        }catch (IndexOutOfBoundsException e) {
            return;
        }
        this.world = player1.getWorld();
        for (Entity entity : world.getEntities()) {
            if(entity.getScoreboardTags() != null) {
                for(String tags : entity.getScoreboardTags()) {
                    if(tags.equalsIgnoreCase("load")) {
                        if(!isLoading.containsKey(entity)) {
                            LoaderHandling newLoader = new LoaderHandling(main, world, entity);
                            pluginManager.registerEvents(newLoader, main);
                            newLoader.initLoadingChunks();
                            isLoading.put(entity, newLoader);
                        }
                        LoaderHandling loaderInstance = isLoading.get(entity);
                        if(loaderInstance.isLoaderCancelled()) {
                            isLoading.remove(entity);
                        }
                    }
                }

            }
        }
    }

    public boolean arePlayersOnline() {
        try {
            playersOnline.addAll(Bukkit.getOnlinePlayers());
            return true;
        }catch(IndexOutOfBoundsException e) {
            return false;
        }
    }
    public void addOnlinePlayers() {
        playersOnline.addAll(Bukkit.getOnlinePlayers());

    }

}
