package nl.officialfox.replaycheat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplayCheat extends JavaPlugin implements Listener {

    private Map<Player, ReplaySession> sessionMap = new HashMap<>();
    private ReplayDatabase database = new ReplayDatabase();

    @Override
    public void onEnable() {
        database.connect();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ReplaySession session = new ReplaySession(player);
        session.startRecording();

        sessionMap.put(player, session);
        database.saveSession(session);
    }

    @Override
    public void onDisable() {
        for (ReplaySession session : sessionMap.values()) {
            session.stopRecording(); //stop sessions
        }
        notifyStaff();
    }

    private void notifyStaff() {
        List<ReplaySession> sessions = database.getNewSessions();

    }

}