package gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import gui.tools.Sidebar;
import gui.views.DownloadsView;
import gui.views.SearchView;

public class MainFrame extends JFrame {
	
	public MainFrame() {
	setSize(1200, 800);
	setTitle("TubeKeeper");

	setLocationRelativeTo(null);
	
	((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	Sidebar sidebar = new Sidebar();
	add(sidebar, BorderLayout.WEST);
	
	SearchView search = new SearchView();
	add(search, BorderLayout.CENTER);
	
	DownloadsView downloads = new DownloadsView();
	//add(downloads, BorderLayout.CENTER);
	
	setVisible(true);
	}
	
}
