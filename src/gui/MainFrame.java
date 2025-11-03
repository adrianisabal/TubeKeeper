package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	public MainFrame() {
	setSize(1200, 800);
	setTitle("TubeKeeper");

	setLocationRelativeTo(null);
	setVisible(true);
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	Sidebar sidebar = new Sidebar();
	add(sidebar, BorderLayout.WEST);
	
	}
	
}
