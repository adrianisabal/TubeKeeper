package utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageUtils { 

  public static ImageIcon resizeImageIcon(ImageIcon imageIcon, int width, int height) {
    Image image = imageIcon.getImage();
		ImageIcon resizedImageIcon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    return resizedImageIcon;
  }
}
