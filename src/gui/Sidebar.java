package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.border.EmptyBorder;

public class Sidebar extends JPanel {
	
	private boolean isExpanded = false; //TODO cambiarla a false porque no queremos que aparezca expandida, pero de momento sí pq no tengo otra cosa hecha
	private List<JButton> menuButtons = new ArrayList<>();
	private JButton expandBut = new JButton("-");
	
	public Sidebar() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(50, 80));
		setBackground(new Color(45,45,48));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		setMaximumSize(new Dimension(750, 80));
		expandedActionListener();
		
		add(expandBut);
		add(new JSeparator());	
		
		String[] names = {"Playlists Menu", "History", "Search", "Video"};
		createButtons(names);
		
		add(Box.createVerticalGlue());		
		add(new JButton("Settings"));
	}
	
	private void createButtons(String[] names) {
		for (int i = 0; i < names.length ; i++) {
			
		JButton newButton = new JButton(names[i]);
		
		newButton.setBackground(new Color(248, 90, 9));
		newButton.setForeground(Color.WHITE);
		newButton.setPreferredSize(new Dimension(20,60));
		
		if (i != names.length) {
			add(Box.createRigidArea(new Dimension(0,30)));
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
				setPreferredSize(new Dimension(750, 80));
				expandBut.setText("Toggle");
			}
		} else {
			for (JButton but : menuButtons) {
				but.setVisible(false);
				setPreferredSize(new Dimension(50, 80));
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
	

}
