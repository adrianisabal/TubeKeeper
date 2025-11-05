package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class SearchView extends JPanel {

	public SearchView() {
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1000, 700));
		
		add(new JLabel("Quick Downloads"), BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbd = defineConstraints();

		defineLogo(centerPanel, gbd);
		defineSearchBar(centerPanel, gbd);
				
		add(centerPanel, BorderLayout.CENTER);
	}
	
	
	private GridBagConstraints defineConstraints() {
		GridBagConstraints gbd = new GridBagConstraints();
		
		gbd.gridx = 0;
		gbd.gridy = 0;
		gbd.anchor = GridBagConstraints.CENTER;
		
		return gbd;
	}
	
	private void defineLogo(JPanel containerPanel, GridBagConstraints gbd) {
		
		ImageIcon logo = new ImageIcon("resources/images/logo.png");
		Image image = logo.getImage();
		ImageIcon resizedLogo = new ImageIcon(image.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
		
		JLabel logoContainer = new JLabel(resizedLogo);

		logoContainer.setPreferredSize(new Dimension(300,300));
		logoContainer.setMaximumSize(new Dimension(500,300));
		
		containerPanel.add(logoContainer, gbd);
	}
	
	
	private void defineSearchBar(JPanel containerPanel, GridBagConstraints gbd) {
		
		SearchBar searcher = new SearchBar();	
		searcher.setText("Insert an URL or Search a video by title.");
		searcher.setIcon("resources/images/searchIcon.png");
				
		searcher.setPreferredSize(new Dimension(300, 40));
		searcher.setMaximumSize(new Dimension(500, 80));
		
		gbd.gridy = 1;
		containerPanel.add(searcher, gbd);

	}
}
