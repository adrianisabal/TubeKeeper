package db;

import domain.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

  public int insert(Playlist p) throws SQLException {
    String sql = "INSERT INTO playlist " +
            "(title, author, description, views, last_updated, owner_id, owner_url, url, thumbnail_path) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      pstmt.setString(1, p.getTitle());
      pstmt.setString(2, p.getAuthor());
      pstmt.setString(3, p.getDescription());
      pstmt.setString(4, p.getViews());
      pstmt.setString(5, p.getLastUpdated());
      pstmt.setString(6, p.getOwnerId());
      pstmt.setString(7, p.getOwnerUrl());
      pstmt.setString(8, p.getUrl());
      pstmt.setString(9, null);

      pstmt.executeUpdate();

      try (ResultSet rs = pstmt.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    }
    return -1;
  }

  public List<Playlist> findAll() throws SQLException {
    List<Playlist> result = new ArrayList<>();

    String sql = "SELECT id, title, author FROM playlist";

    try (Connection con = DatabaseManager.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        Playlist p = new Playlist(id, title, author);
        result.add(p);
      }
    }

    return result;
  }

  public void updateTitle(int id, String newTitle) throws SQLException {
    String sql = "UPDATE playlist SET title = ? WHERE id = ?";

    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setString(1, newTitle);
      pstmt.setInt(2, id);
      pstmt.executeUpdate();
    }
  }

  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM playlist WHERE id = ?";

    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    }
  }

  public int countAll() throws SQLException {
    String sql = "SELECT COUNT(*) FROM playlist";
    try (Connection con = DatabaseManager.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
      if (rs.next()) {
        return rs.getInt(1);
      }
    }
    return 0;
  }
}
