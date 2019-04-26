package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the database access for high scores. Checks for existing table in the database. Uses Sqlite.
 */
public class ScoreDao implements Dao {

    private String url;
    private String createTable;

    public ScoreDao(String databaseUrl) {

        url = databaseUrl;
        createTable = "CREATE TABLE IF NOT EXISTS highscores (id integer PRIMARY KEY, name text NOT NULL, score integer NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
            conn.close();
        } catch (SQLException e) {

        }

    }
    
    /**
     * Create a new database record for a score.
     * @param name Username
     * @param score Score
     * @throws SQLException SQLException
     */

    @Override
    public void create(String name, int score) throws SQLException {

        String query = "INSERT INTO highscores(name, score) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    /**
     * Retrieve all scores from the database.
     * @return List of all saved scores.
     * @throws SQLException SQLException 
     */
    @Override
    public List<String> findAll() throws SQLException {

        List<String> highscores = new ArrayList<>();

        String query = "SELECT name, score FROM highscores ORDER BY score DESC";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                highscores.add(rs.getString("name") + " " + rs.getString("score"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return highscores;
    }
}
