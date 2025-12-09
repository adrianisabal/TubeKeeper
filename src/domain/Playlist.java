package domain;

import javax.swing.ImageIcon;

import com.github.felipeucelli.javatube.Youtube;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

  private int dbId;

  private String id;

  private String title = "New Playlist";
  private String description;
  private String views;
  private String lastUpdated;
  private String author = "Local";
  private String ownerId;
  private String ownerUrl;
  private String url;

  private ArrayList<Video> videos = new ArrayList<>();
  private ArrayList<String> videoUrls = new ArrayList<>();

  private ImageIcon thumbnail;

  public Playlist(String title) {
    this(title, "Local", null);
  }

  public Playlist(String title, String author, ImageIcon thumbnail) {
    this.title = title;
    this.author = author;
    this.thumbnail = thumbnail;
  }

  public Playlist(int dbId, String title, String author) {
    this.dbId = dbId;
    this.title = title;
    this.author = author;
    this.thumbnail = new ImageIcon("resources/images/logo.png");
  }

  public Playlist(com.github.felipeucelli.javatube.Playlist apiPlaylist) throws Exception {
    fillMetadata(apiPlaylist);
  }

  private void fillMetadata(com.github.felipeucelli.javatube.Playlist apiPlaylist) throws Exception {
    this.id = null;

    this.title = apiPlaylist.getTitle();
    this.description = apiPlaylist.getDescription();
    this.views = apiPlaylist.getViews();
    this.lastUpdated = apiPlaylist.getLastUpdated();
    this.author = apiPlaylist.getOwner();
    this.ownerId = apiPlaylist.getOwnerId();
    this.ownerUrl = apiPlaylist.getOwnerUrl();
    this.url = apiPlaylist.getUrl();
    this.videoUrls = apiPlaylist.getVideos();

    this.videos = convertVideos(apiPlaylist);
    this.thumbnail = new ImageIcon(apiPlaylist.getThumbnailUrl());
  }
  
  private ArrayList<Video> convertVideos(com.github.felipeucelli.javatube.Playlist apiPlaylist) {
	  ArrayList<Video> result = new ArrayList<>();
	  
      for (String url : videoUrls) {
          Video v;
		  try {
			Youtube yt = new Youtube(url);
			com.github.felipeucelli.javatube.Stream stream = yt.streams().getHighestResolution();
			v = new Video(yt, stream);
			result.add(v);
		  } catch (Exception e) {
			e.printStackTrace();
		  }
      }

      return result;
  }

  public int getDbId() {
    return dbId;
  }

  public void setDbId(int dbId) {
    this.dbId = dbId;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getViews() {
    return views;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public String getAuthor() {
    return author;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public String getOwnerUrl() {
    return ownerUrl;
  }

  public String length() {
    return String.valueOf(videos.size());
  }

  public String getUrl() {
    return url;
  }

  public ArrayList<Video> getVideos() {
    return videos;
  }

  public ArrayList<String> getVideoUrls() {
    return videoUrls;
  }

  public ImageIcon getThumbnail() {
    return thumbnail;
  }
}
