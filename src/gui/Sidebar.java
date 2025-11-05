package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Sidebar extends JPanel {
	
	private boolean isExpanded = false;
	private List<JButton> menuButtons = new ArrayList<>();
	private JButton expandBut = new JButton("+");
	private JButton settingsBut = new JButton("Settings");
	
	public Sidebar() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(70, 80));
		setBackground(new Color(45,45,48));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setMaximumSize(new Dimension(750, 80));
		
		add(Box.createVerticalStrut(10));
		expandedActionListener();
		expandBut.setAlignmentX(CENTER_ALIGNMENT);
		
		add(expandBut);
		add(new JSeparator());	
		
		String[] names = {"Playlists Menu", "History", "Search", "Video"};
		createButtons(names);
		
		add(Box.createVerticalGlue());
		
		
	}
	
	private void createButtons(String[] names) {
		for (int i = 0; i < names.length ; i++) {
			
		JButton newButton = new JButton(names[i]);
		
		Dimension pref = new Dimension(248,60);
		Font font = new Font("Orbit", 0, 21);
		
		newButton.setBackground(new Color(45,45,48));
//		newButton.setForeground(new Color(248, 90, 9));
		newButton.setForeground(new Color(255, 108, 22));
		newButton.setFont(font);
		newButton.setBorder(new RoundedBorder(30));

		
		newButton.setPreferredSize(pref);
		newButton.setMaximumSize(pref);
		newButton.setAlignmentX(CENTER_ALIGNMENT);
		if (!isExpanded) {
			newButton.setVisible(false);
		}
		
		if (i != names.length) {
			add(Box.createRigidArea(new Dimension(0,15)));
		}
		menuButtons.add(newButton);
		add(newButton);
		}
	}
	
	private void toggleSidebar() {
		isExpanded = !isExpanded;
		
		if (isExpanded) {
			for (JButton but : menuButtons) {
				but.setVisible(true);
				setPreferredSize(new Dimension(250, 80));
				expandBut.setText("Toggle");
			}
		} else {
			for (JButton but : menuButtons) {
				but.setVisible(false);
				setPreferredSize(new Dimension(70, 80));
				expandBut.setText("+");
			}
		}
	}
	
	private void expandedActionListener() {
		expandBut.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	toggleSidebar();
		    }
		});
	}
	
	private void setSettingsButton() {
		settingsBut.setAlignmentX(CENTER_ALIGNMENT);
		settingsBut.setBackground(new Color(248, 90, 9));
		settingsBut.setForeground(new Color(248, 90, 9));
		settingsBut.setBorder(new RoundedBorder(30));
		
		add(settingsBut);
	}
	
	
	
	// Source - https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
	// Posted by Lalchand
	// Retrieved 11/5/2025, License - CC-BY-SA 4.0

	private static class RoundedBorder implements Border {

	    private int radius;


	    RoundedBorder(int radius) {
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
}
