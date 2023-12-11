package nl.officialfox.replaycheat;

import org.bukkit.entity.Player;

import java.util.Date;

public class ReplaySession {

    private Player player;
    private Date startTime;
    private Date endTime;

    private GameplayRecorder recorder;

    public ReplaySession(Player player) {
        this.player = player;
    }

    public void startRecording() {
        startTime = new Date();
        recorder = new GameplayRecorder(player);
        recorder.start();
    }

    public void stopRecording() {
        endTime = new Date();
        recorder.stop();
    }

    public byte[] getRecordedData() {
        return recorder.getRecordedData();
    }

}