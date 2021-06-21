package Kilobytz.miscplugin;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    PluginManager pluginManager = getServer().getPluginManager();
    StatusListener sL = new StatusListener();
    EffectListener eL = new EffectListener();
    StatusHandler sH = new StatusHandler();
    StatusTimer sT = new StatusTimer(this);
    Crafting cR = new Crafting();
    Animations aM = new Animations(this);
    LoaderInit lI = new LoaderInit(this,pluginManager);


    @Override
    public void onEnable() {
        registerListeners();
        loadClasses();
        createConfig();
        startTimer();
    }

    @Override
    public void onDisable() {

    }


    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public void registerListeners() {
        pluginManager.registerEvents(this.sL, this);
        pluginManager.registerEvents(this.eL, this);
        pluginManager.registerEvents(this.aM,this);
    }

    public void loadClasses() {
        cR.addRecipe(this);
        sL.setStatus(sH);
        eL.setStatus(sH);
        eL.loadItems(cR);
        aM.configPop();
    }

    public void startTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                lI.entityCheck();
            }
        },50L, 1L);
    }


    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        Player playerSent = (Player) sender;
        UUID playerID = playerSent.getUniqueId();


        if (command.getName().equalsIgnoreCase("myuuid")) {
            //dp.MessageTest();
            playerSent.sendMessage("My unique user ID is: " + playerID);
            return true;
        }
        if (command.getName().equalsIgnoreCase("heal")) {
            int length = args.length;
            if (length == 1) {
                for (Player toHeal : Bukkit.getServer().getOnlinePlayers()) {
                    if (toHeal.getName().equalsIgnoreCase(args[0])) {
                        toHeal.setHealth(20);
                    }
                }
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("mangleme")) {
        sH.setMangled(playerSent, true);
        return true;
        }
        if (command.getName().equalsIgnoreCase("healmangle")) {
            sH.setMangled(playerSent, false);
            return true;
        }
        if (command.getName().equalsIgnoreCase("manglelist")) {
            ArrayList<Player> viewMangled = new ArrayList<>(sH.getMangledPlayers());
            int mSize = viewMangled.size();
            String mangledList = String.join(", ", viewMangled.toString());
            playerSent.sendMessage(mangledList);
            return true;
        }
        if (command.getName().equalsIgnoreCase("fuckingouch")) {
            if(aM.doesItHurt()) {
                playerSent.sendMessage("hurting shall cease");
                aM.setTheHurt(false);
                return true;
            }
            playerSent.sendMessage("let the hurt commence!");
            aM.setTheHurt(true);
            return true;
        }
        if (command.getName().equalsIgnoreCase("statusgo")) {
            sT.timerStart(playerSent);
            return true;
        }
        if (command.getName().equalsIgnoreCase("statusstop")) {
            sT.timerEnd(playerSent);
            return true;
        }
        return false;
        }

        //find way to figure out player info packet, separate playerlist from skin/cape and player rendering data



}
