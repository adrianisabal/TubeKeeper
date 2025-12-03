package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

  private static final String DB_URL = "jdbc:sqlite:resources/db/tubekeeper.db";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  public static void initializeDatabase() {
    try {
      Class.forName("org.sqlite.JDBC");

      try (Connection con = getConnection();
           Statement stmt = con.createStatement()) {

        String createPlaylistTable =
                "CREATE TABLE IF NOT EXISTS playlist (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  title TEXT," +
                "  author TEXT," +
                "  description TEXT," +
                "  views TEXT," +
                "  last_updated TEXT," +
                "  owner_id TEXT," +
                "  owner_url TEXT," +
                "  url TEXT," +
                "  thumbnail_path TEXT" +
                ");";

        String createVideoTable =
                "CREATE TABLE IF NOT EXISTS video (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  youtube_id TEXT," +
                "  title TEXT," +
                "  author TEXT," +
                "  description TEXT," +
                "  publish_date TEXT," +
                "  length_seconds INTEGER," +
                "  thumbnail_url TEXT," +
                "  views INTEGER," +
                "  url TEXT," +
                "  file_size INTEGER," +
                "  playlist_id INTEGER," +
                "  FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE SET NULL" +
                ");";

        stmt.execute(createPlaylistTable);
        stmt.execute(createVideoTable);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
