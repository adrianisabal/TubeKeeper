package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.util.Properties;
import javax.swing.filechooser.FileSystemView;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ConfigManager {

  private static final String CONFIG_DIR = "conf";
  private static final String CONFIG_FILE = CONFIG_DIR + File.separator + "config.properties";

  private final Properties properties = new Properties();

  public ConfigManager() {
    load();
  }

  private void load() {
    try {
      File dir = new File(CONFIG_DIR);
      if (!dir.exists()) {
        dir.mkdirs();
      }

      File file = new File(CONFIG_FILE);
      if (file.exists()) {
        try (FileInputStream fis = new FileInputStream(file)) {
          properties.load(fis);
        }
      } else {
        setDefaults();
        save();
      }
    } catch (IOException e) {
      e.printStackTrace();
      setDefaults();
    }
  }
 
  public void setDefaults() {
    properties.setProperty("manualRes", "false");
    properties.setProperty("resolution", getDefaultResolution());
    properties.setProperty("downloadPath", getDefaultDownloadPath());
    properties.setProperty("defaultSort", "Most Recent");
    properties.setProperty("saveHistory", "true");
    properties.setProperty("demoMode", "true");
    properties.setProperty("fileType", "mp4");
    properties.setProperty("policy", "Highest quality");
  }

  public void save() {
    try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
      properties.store(fos, "TubeKeeper configuration");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getDefaultDownloadPath() {
    File homeDir = FileSystemView.getFileSystemView().getHomeDirectory();
    File downloadsDir = new File(homeDir, "Downloads");
    if (!downloadsDir.exists() || !downloadsDir.isDirectory()) {
        downloadsDir = new File(homeDir, "Downloads");
    }
    if (!downloadsDir.exists()) {
        downloadsDir.mkdirs();
    }
    return downloadsDir.getAbsolutePath().toString();
  }
 
  private String getDefaultResolution() {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    return toolkit.getScreenSize().toString();
  }

  public boolean isManualRes() {
    return Boolean.parseBoolean(properties.getProperty("manualRes"));
  }

  public void setManualRes(boolean b) {
    properties.setProperty("manualRes", Boolean.toString(b));
  }

  public String getResolution() {
    return properties.getProperty("resolution");
  }

  public void setResolution(Dimension resolution) {
    properties.setProperty("resolution", resolution.toString());
  }

  public String getDownloadPath() {
    return properties.getProperty("downloadPath");
  }

  public void setDownloadPath(String downloadPath) {
    properties.setProperty("downloadPath", downloadPath);
  }

  public String getDefaultSort() {
    return properties.getProperty("defaultSort");
  }

  public void setDefaultSort(String defaultSort) {
    properties.setProperty("defaultSort", defaultSort);
  }

  public boolean isSaveHistory() {
    return Boolean.parseBoolean(properties.getProperty("saveHistory"));
  }

  public void setSaveHistory(boolean saveHistory) {
    properties.setProperty("saveHistory", Boolean.toString(saveHistory));
  }

  public boolean isDemoMode() {
    return Boolean.parseBoolean(properties.getProperty("demoMode"));
  }

  public void setDemoMode(boolean b) {
    properties.setProperty("demoMode", Boolean.toString(b));
  }

  public String getFileType () {
    return properties.getProperty("fileType");
  }

  public void setFileType (String s) {
    properties.setProperty("fileType", s);
  }

  public String getPolicy () {
    return properties.getProperty("policy");
  }

  public void setPolicy (String s) {
    properties.setProperty("policy", s);
  }
}
