package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file. Paths;
import java.time. Instant;
import java.util. function.BiConsumer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.github.felipeucelli. javatube. Playlist;
import com.github. felipeucelli.javatube.Youtube;

import io.ConfigManager;
import io.FFmpegManager;

public class TubeUtils {

  private static class TubeThread extends Thread {

    private String videoLink;
    private JPanel currentPanel;
    private String downloadPath;
    private Runnable onComplete;
    
    public TubeThread(String videoLink, JPanel currentPanel, String downloadPath) {
        this(videoLink, currentPanel, downloadPath, null);
    }

    public TubeThread(String videoLink, JPanel currentPanel, String downloadPath, Runnable onComplete) {
      this.videoLink = videoLink;
      this.currentPanel = currentPanel;
      this.downloadPath = downloadPath;
      this. onComplete = onComplete;
    }

    @Override
    public void run() {
      try {
        downloadVideo(videoLink, currentPanel, downloadPath);
        if (onComplete != null) {
            onComplete.run();
        }
      } catch (Exception e) {
        System.err.println("TubeThread failed for:  " + videoLink);
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

  public static void downloadVideo (String videoLink,
                                    JPanel currentPanel,
                                    String downloadPath) throws Exception {
      downloadVideo(videoLink, currentPanel, downloadPath, null);
  }

  public static void downloadVideo (String videoLink,
                                    JPanel currentPanel,
                                    String downloadPath,
                                    BiConsumer<Long, Long> progressCallback) throws Exception {
    try {
      ConfigManager cfg = new ConfigManager();
      String timeStamp = Long.toString(Instant.now().toEpochMilli());
      Youtube yt = new Youtube(videoLink, "ANDROID");
      String rawTitle = yt.getTitle();
      String safeTitle = sanitizeFilename(rawTitle);

      System.out.println("Downloading: " + safeTitle);

      if (cfg.getFileType().equals("mp3")) {
        Files.deleteIfExists(Paths.get(downloadPath + File.separator + safeTitle + ".mp3"));
        String tmpName = downloadPath + File.separator + "audio" + timeStamp + ".mp4";

        yt.streams().getOnlyAudio().download(
            downloadPath,
            "audio" + timeStamp,
            progressCallback
        );

        System.out.println("Converting to mp3:  " + safeTitle);
        ProcessBuilder pb = new ProcessBuilder(FFmpegManager.getFFmpegPath(), "-i", tmpName,
             downloadPath + safeTitle + ".mp3");
        Process p = pb.start();
        
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line;
        while ((line = errorReader. readLine()) != null) {
            System.err.println("FFmpeg: " + line);
        }
        
        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new Exception("FFmpeg conversion to mp3 failed with code " + exitCode);
        }
        
        Files.delete(Paths.get(tmpName));
        System.out.println("Finished mp3: " + safeTitle);

      } else {
        Files.deleteIfExists(Paths.get(downloadPath + safeTitle + ".mp4"));
        
        if (cfg.getPolicy().equals("Light (360p max, no FFmpeg)")) {
          yt.streams().getDefaultResolution().download(
              downloadPath,
              safeTitle,
              progressCallback
          );
          System.out.println("Finished (Light): " + safeTitle);

        } else {
          File tempDir = new File(downloadPath + "tmp" + timeStamp);
          tempDir.mkdirs();
          String tempPath = downloadPath + tempDir.getName() + File.separator;

          System.out.println("Downloading video stream for: " + safeTitle);
          yt.streams().getHighestResolution().download(
              tempPath,
              "video",
              (bytes, total) -> {
                  if (progressCallback != null && total > 0) {
                      long percent = (bytes * 50) / total;
                      progressCallback.accept(percent, 100L);
                  }
              }
          );

          System.out.println("Downloading audio stream for: " + safeTitle);
          yt.streams().getOnlyAudio().download(
              tempPath,
              "audio",
              (bytes, total) -> {
                  if (progressCallback != null && total > 0) {
                      long percent = 50 + (bytes * 50) / total;
                      progressCallback.accept(percent, 100L);
                  }
              }
          );

          System. out.println("Merging with FFmpeg: " + safeTitle);
          ProcessBuilder pb = new ProcessBuilder(
              FFmpegManager.getFFmpegPath(), 
              "-i", tempPath + "video.mp4",
              "-i", tempPath + "audio.mp4", 
              "-c", "copy",
              downloadPath + safeTitle + ".mp4"
          );
          
          Process p = pb.start();
          
          BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
          String line;
          while ((line = errorReader. readLine()) != null) {
              System.err.println("FFmpeg: " + line);
          }
          
          int exitCode = p.waitFor();
          if (exitCode != 0) {
              System.err.println("FFmpeg failed for: " + safeTitle + " (exit code " + exitCode + ")");
              throw new Exception("FFmpeg merge failed with code " + exitCode);
          }
          
          System.out.println("Deleting temp folder for: " + safeTitle);
          deleteFilesRecursively(new File(tempPath));
          System.out.println("Finished (Highest): " + safeTitle);
        }
      }
    } catch (Exception e) {
      System.err.println("Error in TubeUtils.downloadVideo for URL: " + videoLink);
      e.printStackTrace();
      throw e;
    }
  }

  public static void downloadPlaylist(String plLink,
                                      JPanel currentPanel,
                                      BiConsumer<Integer, Integer> onVideoComplete) throws Exception {
    ConfigManager cfg = new ConfigManager();
    String defaultDownloadPath = cfg.getDownloadPath() + File.separator;
    try { 
      Playlist pl = new Playlist(plLink);
      String playlistTitle = sanitizeFilename(pl.getTitle());
      String downloadPath = defaultDownloadPath + playlistTitle + File.separator;
      new File(downloadPath).mkdirs();

      java.util.List<String> videos = pl.getVideos();
      int total = videos.size();
      java.util.concurrent.atomic.AtomicInteger completed = new java.util.concurrent.atomic.AtomicInteger(0);

      System.out. println("Starting playlist download: " + playlistTitle + " (" + total + " videos)");

      for(String videoLink : videos){
        TubeThread thread = new TubeThread(videoLink, currentPanel, downloadPath, () -> {
            int done = completed.incrementAndGet();
            System.out.println("Playlist progress: " + done + "/" + total);
            if (onVideoComplete != null) {
                onVideoComplete.accept(done, total);
            }
        });
        thread.start();
        Thread.sleep(3000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      SwingUtilities.invokeLater(() -> 
          JOptionPane.showMessageDialog(currentPanel,
            "An error ocurred during playlist download.\n" +
            "Please check your internet connection, \n" +
            "read/write access of TubeKeeper's download folder\n" +
            "and disk space left, then try again.",
            "Error:  Could not download video",
            JOptionPane.ERROR_MESSAGE)
      );
      throw e;
    }
  }

  public static void downloadPlaylist(String plLink, JPanel currentPanel) throws Exception {
      downloadPlaylist(plLink, currentPanel, null);
  }

  private static void deleteFilesRecursively(File startDir) {
    try {
      if (! startDir.isDirectory()) {
        Files.delete(Paths.get(startDir.getPath()));
      } else {
        for (File f : startDir.listFiles()) {
          deleteFilesRecursively(f);
        }
        Files.delete(Paths.get(startDir.getPath()));
      }
    } catch (Exception e) {
      System.err.println("Failed to delete:  " + startDir.getPath());
      e.printStackTrace();
    }
  }

  public static String sanitizeFilename(String name) {
    return name.replaceAll("[\\\\/: *?\"<>|]", "_").trim();
  }
}
