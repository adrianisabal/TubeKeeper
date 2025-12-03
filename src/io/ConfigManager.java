package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.util.Properties;

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

  private void setDefaults() {
    properties.setProperty("language", "Spanish");
    properties.setProperty("theme", "Bright");
    properties.setProperty("defaultSort", "Most Recent");
    properties.setProperty("shareData", "true");
  }

  public void save() {
    try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
      properties.store(fos, "TubeKeeper configuration");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getLanguage() {
    return properties.getProperty("language");
  }

  public void setLanguage(String language) {
    properties.setProperty("language", language);
  }

  public String getTheme() {
    return properties.getProperty("theme");
  }

  public void setTheme(String theme) {
    properties.setProperty("theme", theme);
  }

  public String getDefaultSort() {
    return properties.getProperty("defaultSort");
  }

  public void setDefaultSort(String defaultSort) {
    properties.setProperty("defaultSort", defaultSort);
  }

  public boolean isShareData() {
    return Boolean.parseBoolean(properties.getProperty("shareData", "true"));
  }

  public void setShareData(boolean shareData) {
    properties.setProperty("shareData", Boolean.toString(shareData));
  }
}
