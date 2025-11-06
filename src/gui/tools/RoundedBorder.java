package gui.tools;

import javax.swing.border.Border;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Graphics;

// Source - https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
// Posted by Lalchand
// // Retrieved 2025-11-06, License - CC BY-SA 3.0

public class RoundedBorder implements Border {

    private int radius;


    public RoundedBorder(int radius) {
        this.radius = radius;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}

// END - https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
