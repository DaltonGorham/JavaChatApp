package ClientServerNetwork;


import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper {
    private static final String URL = "jdbc:postgresql://localhost:5432/chatappdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, hh:mm a");

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    // Insert a new chat message
    public static void saveMessage(String username, String message){
        String sql = "INSERT INTO messages (username, message) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void saveDirectMessage(String sender, String recipient, String message){
        String sql = "INSERT INTO direct_messages (sender, recipient, message) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient);
            stmt.setString(3, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDirectMessages(String user1, String user2) {
        List<String> messages = new ArrayList<>();
        String sql = """
        SELECT sender, recipient, message, timestamp 
        FROM direct_messages
        WHERE (sender = ? AND recipient = ?) OR (sender = ? AND recipient = ?)
        ORDER BY timestamp ASC
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user1);
            stmt.setString(2, user2);
            stmt.setString(3, user2);
            stmt.setString(4, user1);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String sender = rs.getString("sender");
                String msg = rs.getString("message");
                Timestamp time = rs.getTimestamp("timestamp");

                LocalDateTime timestamp = time.toLocalDateTime();
                String formattedTime = timestamp.format(formatter);
                String formatted = sender + ": " + msg + "\n(" + formattedTime + ")\n";
                messages.add(formatted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    // (Optional) Load all past chat messages
    public static List<String> loadAllMessages() {
        List<String> messages = new ArrayList<>();
        String sql = "SELECT username, message, timestamp FROM messages ORDER BY timestamp ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String user = rs.getString("username");
                String msg = rs.getString("message");
                Timestamp time = rs.getTimestamp("timestamp");
                LocalDateTime timestamp = time.toLocalDateTime();
                String formattedTime = timestamp.format(formatter);
                String formatted = user + ": " + msg + "\n(" + formattedTime + ")\n";
                messages.add(formatted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
