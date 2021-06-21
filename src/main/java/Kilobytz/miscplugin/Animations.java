package Kilobytz.miscplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.reflect.InvocationTargetException;

public class Animations implements Listener {

    private Main main;

    public Animations(Main main) { this.main = main; }



    private boolean hurtingALot;

    public void configPop() {
        try{
            this.hurtingALot = main.getConfig().getBoolean("HurtToggle = ");
        }catch(NullPointerException e) {
            hurtingALot = false;
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(hurtingALot) {
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
            PacketContainer packetHurt = protocolManager.createPacket(PacketType.Play.Server.ANIMATION);
            PacketContainer packetCrit = protocolManager.createPacket(PacketType.Play.Server.ANIMATION);
            packetHurt.getModifier().writeDefaults();
            packetCrit.getModifier().writeDefaults();
            packetHurt.getIntegers().write(0,event.getPlayer().getEntityId()).write(1,1);
            packetCrit.getIntegers().write(0,event.getPlayer().getEntityId()).write(1,4);
            try {
                protocolManager.sendServerPacket(event.getPlayer(), packetHurt);
                protocolManager.sendServerPacket(event.getPlayer(), packetCrit);
            }catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean doesItHurt() {
        return this.hurtingALot;
    }
    public void setTheHurt(boolean ouch) {
        this.hurtingALot = ouch;
        main.getConfig().set("HurtToggle = " , ouch);
        main.saveConfig();
    }
}
