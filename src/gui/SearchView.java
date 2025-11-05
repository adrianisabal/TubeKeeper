package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchView extends JPanel {

	public SearchView() {
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1000, 700));
		
		
		JTextField searcher = new JTextField();	
		searcher.setText("Insert an URL or Search a video by title.");
		searcher.setBorder(BorderFactory.createEmptyBorder(0,10, 0,0));
		
		searcher.setPreferredSize(new Dimension(300, 40));
		searcher.setMaximumSize(new Dimension(500, 80));
		
		Box box =Box.createVerticalBox();
		
		box.add(Box.createVerticalGlue());
		box.add(searcher, BorderLayout.CENTER);
		box.add(Box.createVerticalGlue());
		
		add(box, BorderLayout.CENTER);
	}
}
