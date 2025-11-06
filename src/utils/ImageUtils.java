package utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageUtils {
  public static ImageIcon getResizedImageIcon(String path, int width, int height) {
    ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage();
		ImageIcon resizedImageIcon = new ImageIcon(image.getScaledInstance(width, width, Image.SCALE_SMOOTH));
    return resizedImageIcon;
  }
}
