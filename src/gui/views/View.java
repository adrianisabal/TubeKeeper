package gui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.ImageUtils;

public class View extends JPanel {
	private ViewType type;
	private String title;
	
	private JPanel headerPanel;
	private JLabel titleContainer;
	private JButton backButton;
	
	public enum ViewType {
		MAIN_VIEW, SUB_VIEW
	}
	
	public View(ViewType type, String title) {
		this.type = type;
		this.title = title;
		
		setLayout(new BorderLayout());
		setHeaderPanel();
		
		setPreferredSize(new Dimension(1000, 700));
		setBorder(BorderFactory.createEmptyBorder(10,20,20,0));
	}
		
	private void setHeaderPanel() {
		this.headerPanel = new JPanel();
		this.headerPanel.setLayout(new GridBagLayout());
		
		add(this.headerPanel, BorderLayout.NORTH);

		GridBagConstraints gbc = defineConstraints();
		
		if (this.type.equals(ViewType.MAIN_VIEW)) {
			setTitle(gbc);
			
		} else {
			gbc.insets = new Insets(0, 0, 5, 0);
			setBackButton(gbc);
			
			if (this.title != null) {
				gbc.gridy = 1;
				setTitle(gbc);
			}
		} 
	}
	
	
	public JPanel getHeaderPanel() {
		return this.headerPanel;
	}
	
	
	private GridBagConstraints defineConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 0, 0, 0);
		
		return gbc;
	}

	
	
	private void setTitle(GridBagConstraints gbc) {
		this.titleContainer = new JLabel(this.title);
		
		this.titleContainer.setFont(new Font(null,1,28));		
		this.titleContainer.setAlignmentX(LEFT_ALIGNMENT);
		
		this.headerPanel.add(titleContainer, gbc);
	}
	
	public JLabel getTitleContainer() {
		return this.titleContainer;
	}

	
	private void setBackButton(GridBagConstraints gbc) {
		this.backButton = new JButton();
		backButton.setBorder(null);
		backButton.setBackground(new Color(238, 238, 238));
		
		ImageIcon backIcon = new ImageIcon("resources/images/back-arrow.png");
		this.backButton.setIcon(ImageUtils.resizeImageIcon(backIcon, 40, 40));
		
		this.headerPanel.add(this.backButton, gbc);
	}
	
	public JButton getBackButton() {
		return this.backButton;
	}
}

