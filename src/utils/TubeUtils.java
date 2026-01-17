package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.Instant;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;

import com.github.felipeucelli.javatube.Playlist;
import com.github.felipeucelli.javatube.Youtube;

import io.ConfigManager;
import io.FFmpegManager;

public class TubeUtils {

  private static class TubeThread extends Thread {

    private String videoLink;
    private JPanel currentPanel;
    private String downloadPath;
    
    public TubeThread(String videoLink, JPanel currentPanel, String downloadPath) {
      this.videoLink = videoLink;
      this.currentPanel = currentPanel;
      this.downloadPath = downloadPath;
    }

    @Override
    public void run() {
      try {
        downloadVideo(videoLink, currentPanel, downloadPath);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  public static void downloadVideo (String videoLink, JPanel currentPanel) {
     try {
        ConfigManager cfg = new ConfigManager();
        String downloadPath = cfg.getDownloadPath() + File.separator;
        downloadVideo(videoLink, currentPanel, downloadPath);
     } catch (Exception e) {
       e.printStackTrace();
     }
  }
  
  public static void downloadVideo (String videoLink, JPanel currentPanel, String downloadPath) throws Exception {
    try {
      ConfigManager cfg = new ConfigManager();
      String timeStamp = Long.toString(Instant.now().toEpochMilli());
      Youtube yt = new Youtube(videoLink, "ANDROID");
      String rawTitle = yt.getTitle();
      String safeTitle = sanitizeFilename(rawTitle);
      if (cfg.getFileType().equals("mp3")) {
        Files.deleteIfExists(Paths.get(downloadPath + File.separator + safeTitle + ".mp3"));
        String tmpName = downloadPath + File.separator + "audio" + timeStamp + ".mp4";
        yt.streams().getOnlyAudio().download(downloadPath, "audio" + timeStamp);
        ProcessBuilder pb = new ProcessBuilder(FFmpegManager.getFFmpegPath(), "-i", tmpName,
             downloadPath + safeTitle + ".mp3");
        Process p = pb.start();
        p.waitFor();
        Files.delete(Paths.get(tmpName));

      } else {
        Files.deleteIfExists(Paths.get(downloadPath + safeTitle + ".mp4"));
        if (cfg.getPolicy().equals("Light (360p max, no FFmpeg)")) {
          yt.streams().getDefaultResolution().download(downloadPath);

        } else {
          File tempDir = new File(downloadPath + "tmp" + timeStamp);
          tempDir.mkdirs();
          String tempPath = downloadPath + tempDir.getName() + File.separator;

          if (cfg.getPolicy().equals("Highest quality")) {
            yt.streams().getHighestResolution().download(tempPath, "video");
            } else {
            yt.streams().getLowestResolution().download(tempPath, "video");
          }
          yt.streams().getOnlyAudio().download(tempPath, "audio");
          ProcessBuilder pb = new ProcessBuilder(FFmpegManager.getFFmpegPath(), "-i", tempPath + "video.mp4",
                                                 "-i", tempPath + "audio.mp4", "-c", "copy",
                                                 downloadPath + safeTitle + ".mp4");
          Process p = pb.start();
          p.waitFor();
          deleteFilesRecursively(new File(tempPath));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static void downloadPlaylist(String plLink, JPanel currentPanel) throws Exception {
    ConfigManager cfg = new ConfigManager();
    String defaultDownloadPath = cfg.getDownloadPath() + File.separator;
    try { 
      Playlist pl = new Playlist(plLink);
      String downloadPath = defaultDownloadPath + pl.getTitle() + File.separator;
      new File(downloadPath).mkdirs();
      for(String videoLink : pl.getVideos()){
        TubeThread thread = new TubeThread(videoLink, currentPanel, downloadPath);
        thread.start();
        TubeThread.sleep(3000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      SwingUtilities.invokeLater(() -> 
          JOptionPane.showMessageDialog(currentPanel,
            "An error ocurred during playlist download.\n" +
            "Please check your internet connection, \n" +
            "read/write access of TubeKeeper's download folder\n" +
            "and disk space left, then try again.",
            "Error: Could not download video",
            JOptionPane.ERROR_MESSAGE)
      );
      throw e;
    }
  }

  private static void deleteFilesRecursively(File startDir) {
    try {
      if (!startDir.isDirectory()) {
        Files.delete(Paths.get(startDir.getPath()));
      } else {
        for (File f : startDir.listFiles()) {
          deleteFilesRecursively(f);
        }
        Files.delete(Paths.get(startDir.getPath()));
      }
    } catch (Exception e) {
      return;
    }
  }

  public static String sanitizeFilename(String name) {
    return name.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
  }
}
