package domain;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

public class Playlist {

  private String id;

  private String title = "New Playlist";
  private String description;
  private String views;
  private String lastUpdated;
  private String owner = "Local";
  private String ownerId;
  private String ownerUrl;
  private String url;

  private ArrayList<String> videoIds = new ArrayList<>();

  private List<Video> videos = new ArrayList<>();

  private ImageIcon thumbnail;

  public Playlist(String title) {
    this(title, "Local", null);
  }

  public Playlist(String title, String owner, ImageIcon thumbnail) {
    this.title = title;
    this.owner = owner;
    this.thumbnail = thumbnail;
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
    this.owner = apiPlaylist.getOwner();
    this.ownerId = apiPlaylist.getOwnerId();
    this.ownerUrl = apiPlaylist.getOwnerUrl();
    this.url = apiPlaylist.getUrl();
    this.videoIds = apiPlaylist.getVideos();  

    this.videos = new ArrayList<>();
    this.thumbnail = null; 
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

  public String getOwner() {
    return owner;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public String getOwnerUrl() {
    return ownerUrl;
  }
  public String length() {
    return String.valueOf(videoIds.size());
  }

  public String getUrl() {
    return url;
  }

  public ArrayList<String> getVideos() {
    return videoIds;
  }

  public List<Video> getVideoObjects() {
    return videos;
  }

  public ImageIcon getThumbnail() {
    return thumbnail;
  }
}
