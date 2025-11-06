package domain;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Playlist {
  private String title = "New Playlist";
  private String author = "Local";
  private ArrayList<Video> videos;
  private ImageIcon thumbnail;

  public Playlist() {}

  public Playlist(String title) {
    this.title = title;
  }
  
  public Playlist(String title, ImageIcon thumbnail){
    this(title);
    this.thumbnail = thumbnail;
  }

  public Playlist(String title, String author, ImageIcon thumbnail) {
    this(title);
    this.author = author;
    this.thumbnail = thumbnail;
  }

  public ImageIcon getThumbnail() {
    return this.thumbnail;
  }

  public String getTitle() {
    return this.title;
  }

  public String getAuthor() {
    return this.author;
  }
}
