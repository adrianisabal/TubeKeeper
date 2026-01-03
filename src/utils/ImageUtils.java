package utils;

import java.awt.Image;
import java.awt.Dimension;

import javax.swing.ImageIcon;

public class ImageUtils { 

  public static ImageIcon resizeImageIcon(ImageIcon imageIcon, int width, int height) {
    Image image = imageIcon.getImage();
		ImageIcon resizedImageIcon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    return resizedImageIcon;
  }

  public static Dimension parseDimension(String dimension) {
    String width = ""; 
    String height = "";
    boolean commaFound = false;
    for (char c : dimension.toCharArray()) {
      if (Character.isDigit(c)) {
         if (!commaFound) {
           width = width + c;
         } else {
           height = height + c;
         }
      }
      if (c == ",".charAt(0)) {
        commaFound = true;
      }
    }
    return new Dimension(Integer.parseInt(width), Integer.parseInt(height));
  }
}
