package domain;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.felipeucelli.javatube.Youtube;
import com.github.felipeucelli.javatube.Stream;
import org.json.JSONArray;

public class Video {

  private String id;
  private String title;
  private String description;
  private String author;
  private String publishDate;
  private Integer length;
  private String thumbnailUrl;
  private Long views;
  private final List<String> keywords = new ArrayList<>();

  private String url;
  private Integer bitrate;
  private Integer fps;
  private String resolution;
  private long fileSize;
  private boolean includeAudioTrack;
  private boolean includeVideoTrack;
  private boolean includesMultipleAudioTracks;
  private boolean defaultAudioTrack;
  private String audioTrackName;
  private String audioTrackId;

  private ImageIcon thumbnail = new ImageIcon("resources/images/logo.png");

  public Video(String title, String author) {
    this(title, author, new ImageIcon("resources/images/logo.png"));
  }

  public Video(String title, String author, ImageIcon thumbnail) {
	  this.title = title;
	  this.author = author;
	  this.thumbnail = thumbnail;
  }
  
  public Video(String title, String author, ImageIcon thumbnail, long fileSize) {
    this.title = title;
    this.author = author;
    this.thumbnail = thumbnail;
    this.fileSize = fileSize;
  }

  public Video(Youtube youtube, Stream stream) throws Exception {
    fillMetadata(youtube, stream);
  }

  public Video(Youtube youtube) throws Exception {
    fillMetadata(youtube, null);
  }

  private void fillMetadata(Youtube youtube, Stream stream) throws Exception {
    String watchUrl = youtube.getUrl();
    this.id = extractVideoId(watchUrl);

    this.title = youtube.getTitle();
    this.description = youtube.getDescription();
    this.publishDate = youtube.getPublishDate();
    this.length = youtube.length();
    this.thumbnailUrl = youtube.getThumbnailUrl();
    this.views = youtube.getViews();
    this.author = youtube.getAuthor();

    this.keywords.clear();
    JSONArray apiKeywords = youtube.getKeywords();
    if (apiKeywords != null) {
      for (int i = 0; i < apiKeywords.length(); i++) {
        this.keywords.add(apiKeywords.getString(i));
      }
    }

    if (stream != null) {
      this.url = stream.getUrl();
      this.bitrate = stream.getBitrate();
      this.fps = stream.getFps();
      this.resolution = stream.getResolution();
      this.fileSize = stream.getFileSize();

      this.includeAudioTrack = stream.includeAudioTrack();
      this.includeVideoTrack = stream.includeVideoTrack();
      this.includesMultipleAudioTracks = stream.includesMultipleAudioTracks();
      this.defaultAudioTrack = stream.isDefaultAudioTrack();
      this.audioTrackName = stream.getAudioTrackName();
      this.audioTrackId = stream.getAudioTrackId();
    }
  }

  private String extractVideoId(String url) {
    if (url == null) {
      return null;
    }
    Pattern pattern = Pattern.compile("(?:v=|vi=|/v/|/vi/|/e/|/embed/|/shorts/|youtu\\.be/|/watch/)([0-9A-Za-z_-]{11})");
    Matcher matcher = pattern.matcher(url);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return null;
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

  public String getPublishDate() {
    return publishDate;
  }

  public Integer length() {
    return length;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public Long getViews() {
    return views;
  }

  public String getAuthor() {
    return author;
  }

  public List<String> getKeywords() {
    return keywords;
  }

  public String getUrl() {
    return url;
  }

  public Integer getBitrate() {
    return bitrate;
  }

  public Integer getFps() {
    return fps;
  }

  public String getResolution() {
    return resolution;
  }

  public long getFileSize() {
    return fileSize;
  }

  public boolean includeAudioTrack() {
    return includeAudioTrack;
  }

  public boolean includeVideoTrack() {
    return includeVideoTrack;
  }

  public boolean includesMultipleAudioTracks() {
    return includesMultipleAudioTracks;
  }

  public boolean isDefaultAudioTrack() {
    return defaultAudioTrack;
  }

  public String getAudioTrackName() {
    return audioTrackName;
  }

  public String getAudioTrackId() {
    return audioTrackId;
  }

  public ImageIcon getThumbnail() {
    return thumbnail;
  }
}
