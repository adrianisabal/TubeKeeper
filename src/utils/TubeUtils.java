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

  public static void downloadVideo (String videoLink, JPanel currentPanel) throws Exception {
    try {
      ConfigManager cfg = new ConfigManager();
      String downloadPath = cfg.getDownloadPath() + File.separator;
      String timeStamp = Long.toString(Instant.now().toEpochMilli());
      Youtube yt = new Youtube(videoLink);

      if (cfg.getFileType().equals("mp3")) {
        Files.deleteIfExists(Paths.get(downloadPath + File.separator + yt.getTitle() + ".mp3"));
        String tmpName = downloadPath + File.separator + "audio" + timeStamp + ".mp4";
        yt.streams().getOnlyAudio().download(downloadPath, "audio" + timeStamp);
        ProcessBuilder pb = new ProcessBuilder(FFmpegManager.getFFmpegPath(), "-i", tmpName,
             downloadPath + yt.getTitle() + ".mp3");
        Process p = pb.start();
        p.waitFor();
        Files.delete(Paths.get(tmpName));

      } else {
        Files.deleteIfExists(Paths.get(downloadPath + yt.getTitle() + ".mp4"));
        if (cfg.getPolicy().equals("Light (360p max, no FFmpeg)")) {
          yt.streams().getDefaultResolution().download(downloadPath);

        } else {
          File tempDir = new File(downloadPath + "tmp" + timeStamp);
          tempDir.mkdirs();
          downloadPath = downloadPath + tempDir.getName() + File.separator;

          if (cfg.getPolicy().equals("Highest quality")) {
            yt.streams().getHighestResolution().download(downloadPath, "video");
            } else {
            yt.streams().getLowestResolution().download(downloadPath, "video");
          }
          yt.streams().getOnlyAudio().download(downloadPath, "audio");
          ProcessBuilder pb = new ProcessBuilder(FFmpegManager.getFFmpegPath(), "-i", downloadPath + "video.mp4",
                                                 "-i", downloadPath + "audio.mp4", "-c", "copy",
                                                 cfg.getDownloadPath() + File.separator + yt.getTitle() + ".mp4");
          Process p = pb.start();
          p.waitFor();
          deleteFilesRecursively(new File(downloadPath));
        }
      }
    } catch (Exception e) {
      SwingUtilities.invokeLater(() -> 
          JOptionPane.showMessageDialog(currentPanel,
            "An error ocurred during playlist download.",
            "Error: Could not download playlist",
            JOptionPane.ERROR_MESSAGE)
      );
      throw e;
    }
  }

  public static void downloadPlaylist(String plLink, JPanel currentPanel) throws Exception {
    ConfigManager cfg = new ConfigManager();
    String defaultDownloadPath = cfg.getDownloadPath() + File.separator;
    cfg.save();
    try { 
      Playlist pl = new Playlist(plLink);
      String downloadPath = defaultDownloadPath + pl.getTitle() + File.separator;
      new File(downloadPath).mkdirs();
      cfg.setDownloadPath(downloadPath);
      for(String videoLink : pl.getVideos()){
        downloadVideo(videoLink, currentPanel); 
      }
      cfg.setDownloadPath(defaultDownloadPath);
      cfg.save();
    } catch (Exception e) {
      cfg.setDownloadPath(defaultDownloadPath);
      cfg.save();
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
}
