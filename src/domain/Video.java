package domain;

import javax.swing.ImageIcon;

public class Video {
  String title;
  String author;
  ImageIcon thumbnail = new ImageIcon("resources/images/logo.png");

  public Video() {
    
  }

  public Video(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public Video(String title, String author, ImageIcon thumbnail) {
    this(title, author);
    this.thumbnail = thumbnail;
  }

  public String getTitle() {
    return this.title;
  }

  public String getAuthor() {
      return author;
  }

  public ImageIcon getThumbnail() {
      return thumbnail;
  }
}
