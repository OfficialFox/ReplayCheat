package nl.officialfox.replaycheat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReplayDatabase {

    private Connection connection;

    public ReplayDatabase() {
    }

    public void saveSession(ReplaySession session) {
        String sql = "INSERT INTO sessions (player_id, start_time, end_time, gameplay_data) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, session.getPlayerId());
            stmt.setTimestamp(2, session.getStartTime());
            stmt.setTimestamp(3, session.getEndTime());
            stmt.setBytes(4, session.getGameplayData());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving session!");
        }
    }

    public List<ReplaySession> getNewSessions() {
        List<ReplaySession> sessions = new ArrayList<>();
        String sql = "SELECT * FROM sessions WHERE reviewed = 0";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ReplaySession session = new ReplaySession();
                session.setPlayerId(rs.getString("player_id"));
                session.setStartTime(rs.getTimestamp("start_time"));
                session.setEndTime(rs.getTimestamp("end_time"));
                session.setGameplayData(rs.getBytes("gameplay_data"));

                sessions.add(session);
            }
        } catch (SQLException e) {
            System.out.println("Error getting new sessions!");
        }

        return sessions;
    }

}
