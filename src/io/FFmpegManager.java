package io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import java.net.URL;
import java.net.URI;
import javax.net.ssl.HttpsURLConnection;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FFmpegManager {

  private static final String download_URL = isWindows() ? "https://github.com/BtbN/FFmpeg-Builds/releases/download/latest/ffmpeg-master-latest-win64-gpl.zip" :
                                             isMac() ? "https://evermeet.cx/ffmpeg/ffmpeg-8.0.1.zip":
                                             "https://github.com/BtbN/FFmpeg-Builds/releases/download/latest/ffmpeg-master-latest-linux64-gpl.tar.xz";
  private static final String INSTALL_DIR = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "ffmpeg";
  private static final String FFMPEG = "ffmpeg" + (isWindows() ? ".exe" : "");
  private static String archiveName = "";
  private static String ffmpegPath = FFMPEG;
  private static JPanel currentPanel;


  private static class FFmpegThread extends Thread {
  @Override
  public void run() {
      startManager();
    }
  }

  public static void begin(JPanel currentPanel) {
    FFmpegManager.currentPanel = currentPanel;
    FFmpegThread thread = new FFmpegThread();
    thread.start();
  }

  private static void startManager() {
    if (!isFFmpegInstalled()) {
      final int[] confirm = new int[1];
      try {
        SwingUtilities.invokeAndWait(() -> 
        confirm[0] = JOptionPane.showConfirmDialog(currentPanel, 
          "It looks like FFmpeg is not installed in the system.\n" +
          "You can continue using TubeKeeper, but only downloads in " +
          "up to 360p will be supported.\n"+ 
          "Would you like TubeKeeper to install FFmpeg for you?\n" +
          "(Internet connection is required)",
          "Warning: FFmpeg not installed",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE)
        );
        if (confirm[0] == JOptionPane.YES_OPTION) {
          SwingUtilities.invokeAndWait(() -> 
            confirm[0] = JOptionPane.showConfirmDialog(currentPanel,
              "The binaries of FFmpeg will be downloaded now.\n" + 
              "Internet connection (150 ~ 200MB) is needed for this action,\n" + 
              "as well as read and write permissions of TubeKeeper's\n" + 
              "installation directory.\n" +
              "You might keep using the application while this happens,\n" + 
              "but closing it will stop the process and it will have to\n" +
              "start over the next time.\n" +
              "Proceed with the download?",
              "Download FFmpeg?",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.INFORMATION_MESSAGE)
          );
        }
      } catch(Exception e) {
        e.printStackTrace();
        return;
      }
      if (confirm[0] == JOptionPane.OK_OPTION) {
        installFFmpeg();
      }

    }
  }

  private static boolean isWindows() {
     return System.getProperty("os.name").toLowerCase().contains("win");
  }

  private static boolean isMac() {
     return System.getProperty("os.name").toLowerCase().contains("mac");
  }

  public static boolean isFFmpegInstalled() {
    try {
       if (isSuccessfulCommand(new ProcessBuilder(FFMPEG, "-version"))) {
         return true;
       }
       File f = new File(INSTALL_DIR);
       if (f.exists()) {
         File foundFile = findBinaryRecursively(f);
         if (foundFile != null) {
             ffmpegPath = foundFile.getAbsolutePath();
         }
       }
       return isSuccessfulCommand(new ProcessBuilder(ffmpegPath, "-version"));
    } catch (Exception e) {
       return false;
    }
  }

  private static boolean isSuccessfulCommand(ProcessBuilder processBuilder) {
    try {
       Process p = processBuilder.start();
       boolean finished = p.waitFor(5000, TimeUnit.MILLISECONDS);
       if (finished && p.exitValue() == 0) {
          return true;
       }
       p.destroy();
       return false;
    } catch (Exception e) {
      return false;
    }
  }

  private static File findBinaryRecursively(File startDir) {
    if (!startDir.isDirectory()) {
      if (startDir.getName().equals(FFMPEG)) {
        return startDir;
      }
    } else {
      File[] files = startDir.listFiles();
      if (files != null) {
        for (File f : startDir.listFiles()) {
          File found = findBinaryRecursively(f);
          if (found != null) {
            return found;
          }
        }
      }
    }
    return null;
  }

  private static void installFFmpeg() {
    if (!Files.exists(Paths.get(INSTALL_DIR))){
      new File(INSTALL_DIR).mkdirs();
    }
    String[] uriFields = download_URL.toString().split("/");
    archiveName = uriFields[uriFields.length - 1];
    try {
      downloadFile();
      if (isWindows()) {
        unzip();
      } else {
        if (isMac()) {
          unzip();
        } else {
          untarxz();
        }
        setExPermission();
      }
      Files.deleteIfExists(Paths.get(INSTALL_DIR + File.separator + archiveName));
      if(isFFmpegInstalled()) {
        SwingUtilities.invokeLater(() -> 
          JOptionPane.showMessageDialog(currentPanel, 
            "FFmpeg has been successfully installed.\n" + 
            "You can close the application now, changes will stay.\n" +
            "Now you can enjoy full quality downloads.",
            "Success: FFmpeg successfully installed",
            JOptionPane.INFORMATION_MESSAGE)
        );
      }
    } catch (Exception e) {
      SwingUtilities.invokeLater(() -> 
        JOptionPane.showMessageDialog(currentPanel, 
            "An FFmpeg installation error has been reported.\n" +
            "Restarting the application is recommended.",
            "FFmpeg installation error",
            JOptionPane.ERROR_MESSAGE)
      );
    }
  }

  private static void downloadFile() throws Exception {
    try {
      URL url = new URI(download_URL).toURL();
      HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
      con.setRequestProperty("User-Agent", "TubeKeeper/1.0");
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      if (responseCode != HttpsURLConnection.HTTP_OK) {
         con.disconnect();
         throw new IOException("HTTPS Request failed with code " + responseCode);
      }
      try (InputStream inputStream = con.getInputStream()) {
        Files.copy(inputStream, Paths.get(INSTALL_DIR + File.separator + archiveName), StandardCopyOption.REPLACE_EXISTING);
      }
      con.disconnect();
      SwingUtilities.invokeLater(() -> 
        JOptionPane.showMessageDialog(currentPanel,
          "FFmpeg binaries have been successfully downloaded.\n" + 
          "Now the installation will be performed.\n" + 
          "As before, you can continue using the app, \n" +
          "but exiting it will stop the process and\n" +
          "it will need to start over.",
          "FFmpeg binaries downloaded successfully",
          JOptionPane.INFORMATION_MESSAGE)
      );
    } catch (Exception e) {
      SwingUtilities.invokeLater(() -> 
        JOptionPane.showMessageDialog(currentPanel, 
          "An error occurred when downloading the FFmpeg file,\n" +
          "please check your internet connection, TubeKeeper\n" +
          "installation directory read and write permissions,\n" +
          "then restart the app.",
          "Error: Could not download FFmpeg binaries",
          JOptionPane.ERROR_MESSAGE)
      );
      throw e;
      }
  }

  private static void unzip() throws Exception {
    try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(INSTALL_DIR + File.separator + archiveName))) {
      ZipEntry entry = zipIn.getNextEntry();
      while (entry != null) {
          String filePath = INSTALL_DIR + File.separator + entry.getName();
          if (!entry.isDirectory()) {
            File parent = new File(filePath).getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            Files.copy(zipIn, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            String entryFileName = new File(entry.getName()).getName();
            if (entryFileName.equals((isMac()) ? "ffmpeg" :"ffmpeg.exe")) {
              ffmpegPath = new File(filePath).getAbsolutePath();
            }
          } else {
            new File(filePath).mkdir();
          }
          zipIn.closeEntry();
          entry = zipIn.getNextEntry();
      }
    } catch (Exception e) {
      SwingUtilities.invokeLater(() ->
        JOptionPane.showMessageDialog(currentPanel, 
          "An error occurred when extracting the FFmpeg zip file,\n" +
          "please check your TubeKeeper installation directory read\n" +
          "and write permissions, then restart the app.",
          "Error: Could not unzip FFMpeg binaries",
          JOptionPane.ERROR_MESSAGE)
      );
      throw e;
    }
  }

  private static void untarxz() throws Exception {
    try {
      ProcessBuilder processBuilder = new ProcessBuilder("tar", "-xf", INSTALL_DIR + File.separator + archiveName, 
                                                         "-C", INSTALL_DIR + File.separator);
      Process p = processBuilder.start();
      p.waitFor();
      ffmpegPath = findBinaryRecursively(new File(INSTALL_DIR)).getAbsolutePath();
    } catch (Exception e) {
      SwingUtilities.invokeLater(() -> 
        JOptionPane.showMessageDialog(currentPanel, 
          "An error occurred when extracting the FFmpeg tar file,\n" +
          "please check the tar command is available and in PATH and\n" +
          "your TubeKeeper installation directory read and write\n" +
          "permissions, then restart the app.",
          "Error: Could not untar FFMpeg binaries",
          JOptionPane.ERROR_MESSAGE)
        );
      throw e;
    }  
  }

  private static void setExPermission() throws Exception {
    try {
      ProcessBuilder processBuilder = new ProcessBuilder("chmod", "+x", ffmpegPath);
      Process p = processBuilder.start();
      p.waitFor();

      if (isMac()) {
        try {
          ProcessBuilder pbMac = new ProcessBuilder("xattr", "-d", "com.apple.quarantine", ffmpegPath);
          Process pMac = pbMac.start();
          pMac.waitFor();
        } catch (Exception e) {}
      }
    } catch (Exception e) {
      SwingUtilities.invokeLater(() -> 
        JOptionPane.showMessageDialog(currentPanel, 
          "An error occurred when setting the execution permission for FFMpeg.\n" +
          "Please check your TubeKeeper installation directory read\n" +
          "and write permissions, then restart the app.",
          "Error: Chmod +x failed",
          JOptionPane.ERROR_MESSAGE)
      );
      throw e;
    }  
  }

  public static String getFFmpegPath() {
    return ffmpegPath;
  }
}
