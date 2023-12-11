package nl.officialfox.replaycheat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameplayRecorder implements Listener {

    private Player player;

    private List<Location> locations = new ArrayList<>();

    public GameplayRecorder(Player player) {
        this.player = player;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getPlayer() == player) {
            locations.add(event.getTo());
        }
    }

    public void start() {
        System.out.println("Starting recording for " + player.getName());
    }

    public void stop() {
        HandlerList.unregisterAll(this);

    }

    public byte[] getRecordedData() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);

            dataOut.writeInt(locations.size());

            for (Location loc : locations) {
                dataOut.writeDouble(loc.getX());
                dataOut.writeDouble(loc.getY());
                dataOut.writeDouble(loc.getZ());
            }

            dataOut.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
