package gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	
	public MainFrame() {
	setSize(1200, 800);
	setTitle("TubeKeeper");

	setLocationRelativeTo(null);
	
	((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	Sidebar sidebar = new Sidebar();
	add(sidebar, BorderLayout.WEST);
	
	SearchView search_v = new SearchView();
	add(search_v, BorderLayout.CENTER);
	
	setVisible(true);
	}
	
}
