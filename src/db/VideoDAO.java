package db;

import domain.Video;

import javax.swing.ImageIcon;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO {

  public void insert(Video v, Integer playlistId) throws SQLException {
    String sql = "INSERT INTO video (" +
            "youtube_id, title, author, description, publish_date, length_seconds, " +
            "thumbnail_url, views, url, file_size, playlist_id" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setString(1, v.getId());
      pstmt.setString(2, v.getTitle());
      pstmt.setString(3, v.getAuthor());
      pstmt.setString(4, v.getDescription());
      pstmt.setString(5, v.getPublishDate());
      if (v.length() != null) {
        pstmt.setInt(6, v.length());
      } else {
        pstmt.setNull(6, Types.INTEGER);
      }
      pstmt.setString(7, v.getThumbnailUrl());
      if (v.getViews() != null) {
        pstmt.setLong(8, v.getViews());
      } else {
        pstmt.setNull(8, Types.INTEGER);
      }
      pstmt.setString(9, v.getUrl());
      pstmt.setLong(10, v.getFileSize());
      if (playlistId != null) {
        pstmt.setInt(11, playlistId);
      } else {
        pstmt.setNull(11, Types.INTEGER);
      }

      pstmt.executeUpdate();
    }
  }

  private ImageIcon buildThumbnail(String thumbUrl) {
    ImageIcon logo = new ImageIcon("resources/images/logo.png");
    if (thumbUrl == null || thumbUrl.isEmpty()) {
      return logo;
    }
    try {
      ImageIcon fromUrl = new ImageIcon(new URL(thumbUrl));
      if (fromUrl.getIconWidth() > 0 && fromUrl.getIconHeight() > 0) {
        return fromUrl;
      } else {
        return logo;
      }
    } catch (Exception e) {
      return logo;
    }
  }

  public List<Video> findByPlaylistId(int playlistId) throws SQLException {
    List<Video> result = new ArrayList<>();

    String sql = "SELECT title, author, thumbnail_url FROM video WHERE playlist_id = ?";

    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setInt(1, playlistId);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          String title = rs.getString("title");
          String author = rs.getString("author");
          String thumbUrl = rs.getString("thumbnail_url");
          ImageIcon thumb = buildThumbnail(thumbUrl);
          Video v = new Video(title, author, thumb);
          result.add(v);
        }
      }
    }

    return result;
  }

  public List<Video> findAll() throws SQLException {
    List<Video> result = new ArrayList<>();

    String sql = "SELECT title, author, thumbnail_url FROM video";

    try (Connection con = DatabaseManager.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        String title = rs.getString("title");
        String author = rs.getString("author");
        String thumbUrl = rs.getString("thumbnail_url");
        ImageIcon thumb = buildThumbnail(thumbUrl);
        Video v = new Video(title, author, thumb);
        result.add(v);
      }
    }

    return result;
  }

  public void updateTitle(int id, String newTitle) throws SQLException {
    String sql = "UPDATE video SET title = ? WHERE id = ?";

    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setString(1, newTitle);
      pstmt.setInt(2, id);
      pstmt.executeUpdate();
    }
  }

  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM video WHERE id = ?";

    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    }
  }
}
