package gui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;
import gui.tools.SearchBar;
import gui.views.View.ViewType;
import utils.ImageUtils;

import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class SearchView extends View {

	public SearchView() {
		super(ViewType.MAIN_VIEW, "Quick Download");
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = defineConstraints();

		defineLogo(centerPanel, gbc);
		defineSearchBar(centerPanel, gbc);
				
		add(centerPanel, BorderLayout.CENTER);
	}
	
	
	private GridBagConstraints defineConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.anchor = GridBagConstraints.CENTER;
		
		return gbc;
	}
	
	private void defineLogo(JPanel containerPanel, GridBagConstraints gbc) {
		
		ImageIcon resizedLogo = ImageUtils.getResizedImageIcon("resources/images/logo.png", 250, 250);
		JLabel logoContainer = new JLabel(resizedLogo);

		logoContainer.setPreferredSize(new Dimension(300,300));
		logoContainer.setMaximumSize(new Dimension(500,300));
		
		containerPanel.add(logoContainer, gbc);
	}
	
	
	private void defineSearchBar(JPanel containerPanel, GridBagConstraints gbc) {
		
		SearchBar searcher = new SearchBar("Insert an URL or Search a video by title.");	
				
		searcher.setPreferredSize(new Dimension(300, 40));
		searcher.setMaximumSize(new Dimension(500, 80));
		
		gbc.gridy = 1;

		containerPanel.add(searcher, gbc);
		
		addEmptySeparator(containerPanel, gbc);
	}
	
	private void addEmptySeparator(JPanel containerPanel, GridBagConstraints gbc) {
		JLabel emptySep = new JLabel();
		
		emptySep.setPreferredSize(new Dimension(50, 150));
		
		gbc.gridy = 2;
		
		containerPanel.add(emptySep, gbc);
		
	}
}
