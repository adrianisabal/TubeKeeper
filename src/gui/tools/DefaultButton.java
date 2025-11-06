package gui.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class DefaultButton extends JButton{
	
    private static final long serialVersionUID = 1L;

    private int borderRadius = 30;

    public static final Dimension DEFAULT_SIZE = new Dimension(210, 60);
    public static final Font DEFAULT_FONT = new Font("Orbit", Font.PLAIN, 21);
    public static final Color DEFAULT_BG = new Color(255,255,255);
    public static final Color DEFAULT_FG = new Color(255, 108, 22);
    public static final int DEFAULT_RADIUS = 30;
	

    public DefaultButton(String text) {
        super(text);

        this.setPreferredSize(DEFAULT_SIZE);
        this.setMaximumSize(DEFAULT_SIZE);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBackground(DEFAULT_BG);
        this.setForeground(DEFAULT_FG);
        this.setFont(DEFAULT_FONT);

        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(DEFAULT_RADIUS));
    }
    
    public DefaultButton(ImageIcon icon) {
        super(icon);

        this.setPreferredSize(DEFAULT_SIZE);
        this.setMaximumSize(DEFAULT_SIZE);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBackground(DEFAULT_BG);
        this.setForeground(DEFAULT_FG);
        this.setFont(DEFAULT_FONT);

        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(DEFAULT_RADIUS));
    }
    
    public DefaultButton(String text, Dimension pref) {
        super(text);

        this.setPreferredSize(pref);
        this.setMaximumSize(pref);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBackground(DEFAULT_BG);
        this.setForeground(DEFAULT_FG);
        this.setFont(DEFAULT_FONT);

        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(DEFAULT_RADIUS));
    }
    
    public DefaultButton(String text, Dimension pref, Font font) {
        super(text);

        this.setPreferredSize(pref);
        this.setMaximumSize(pref);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBackground(DEFAULT_BG);
        this.setForeground(DEFAULT_FG);
        this.setFont(font);

        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(DEFAULT_RADIUS));
    }

    public DefaultButton(String text, Dimension pref, Font font, Color backColor, Color frontColor) {
        super(text);

        this.setPreferredSize(pref);
        this.setMaximumSize(pref);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBackground(backColor);
        this.setForeground(frontColor);
        this.setFont(font);

        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(DEFAULT_RADIUS));
    }

    public DefaultButton(String text, Dimension pref, Font font, Color backColor, Color frontColor, int borderRadius) {
        super(text);
        this.borderRadius = borderRadius;

        this.setPreferredSize(pref);
        this.setMaximumSize(pref);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBackground(backColor);
        this.setForeground(frontColor);
        this.setFont(font);

        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(this.borderRadius));
    }
	
    public DefaultButton(Icon iconExpand, Dimension pref) {
    	super(iconExpand);

        this.setPreferredSize(pref);
        this.setMaximumSize(pref);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBackground(DEFAULT_BG);
        this.setForeground(DEFAULT_FG);
        this.setFont(DEFAULT_FONT);

        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(DEFAULT_RADIUS));
	}

	public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        this.setBorder(new RoundedBorder(this.borderRadius));
        this.repaint();
    }

    public void setCollapsed(boolean collapsed) {
    	this.setVisible(!collapsed);
    }

    public void setNewDimension(Dimension dim) {
    	this.setPreferredSize(dim);
    	this.setMaximumSize(dim);
    	this.repaint();
    }
    
		public void setFont(String string, int plain, int i) {
			Font font = new Font(string, plain, i);
			this.setFont(font);
			
		}
}
