package gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Sidebar extends JPanel {
	
	public Sidebar() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(750, 80));
		
		add(new JButton("Expand"));		
		//add(new JSeparator());
		
		add(new JButton("Playlists Menu"));
		add(new JButton("History"));
		add(new JButton("Search"));
		
		add(Box.createVerticalGlue());
		
		add(new JButton("Settings"));

			
	}
	
	

}
