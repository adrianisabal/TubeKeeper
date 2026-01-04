package gui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.github.felipeucelli.javatube.Youtube;

import db.VideoDAO;
import domain.DownloadManager;
import domain.Video;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import utils.ImageUtils;
import gui.tools.DownloadsPanel;
import gui.tools.SearchBar;

public class SearchView extends View {

	private DownloadManager downloadsManger;
	
	public SearchView() {
		super(ViewType.MAIN_VIEW, "Quick Download");
		
		defineDownloadsPanel();
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = defineConstraints();

		defineLogo(centerPanel, gbc);
		defineSearchBar(centerPanel, gbc);
				
		add(centerPanel, BorderLayout.CENTER);
	}
	
	private void defineDownloadsPanel() {
		DownloadsPanel downloadsPanel = new DownloadsPanel();
		
		add(downloadsPanel, BorderLayout.EAST);
		downloadsPanel.setVisible(false);
		
		downloadsManger = new DownloadManager(downloadsPanel);
	}
	
	private GridBagConstraints defineConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.anchor = GridBagConstraints.CENTER;
		
		return gbc;
	}
	
	private void defineLogo(JPanel containerPanel, GridBagConstraints gbc) {
		
		ImageIcon resizedLogo = ImageUtils.resizeImageIcon(new ImageIcon("resources/images/logo.png"), 250, 250);
		JLabel logoContainer = new JLabel(resizedLogo);

		logoContainer.setPreferredSize(new Dimension(300,300));
		logoContainer.setMaximumSize(new Dimension(500,500));
		
		containerPanel.add(logoContainer, gbc);
	}
	
	
	private void defineSearchBar(JPanel containerPanel, GridBagConstraints gbc) {
		
		SearchBar searcher = new SearchBar("Insert an URL");	
		
		gbc.gridy = 1;

		containerPanel.add(searcher, gbc);
		
		addEmptySeparator(containerPanel, gbc);
		
		searcher.setOnEnterAction(txt -> downloadsManger.download(txt));
	}		
	
	private void addEmptySeparator(JPanel containerPanel, GridBagConstraints gbc) {
		JLabel emptySep = new JLabel();
		
		emptySep.setPreferredSize(new Dimension(50, 150));
		
		gbc.gridy = 2;
		
		containerPanel.add(emptySep, gbc);
		
	}
}
