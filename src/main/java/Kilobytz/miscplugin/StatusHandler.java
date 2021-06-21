package Kilobytz.miscplugin;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class StatusHandler {

    private ArrayList<Player> mangled = new ArrayList<Player>();

    public void setMangled(Player player, Boolean state) {
        if (state) {
            if (!mangled.contains(player)) {
                mangled.add(player);
                player.sendMessage("You have been Mangled!");
            }
            else {
                return;
            }
            }
        else {
            mangled.remove(player);
            player.sendMessage("The mangled flesh is healed.");
        }
    }

    public ArrayList<Player> getMangledPlayers() {
        return mangled;
    }
    public boolean checkMangledPlayer(Player player) {
        if(mangled.contains(null)) {
            return false;
        }
        else {


            if (mangled.contains(player)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
