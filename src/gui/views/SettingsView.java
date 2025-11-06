package gui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.tools.DefaultButton;
import gui.views.View.ViewType;

public class SettingsView extends View{

	public SettingsView() {
		super(ViewType.MAIN_VIEW, "Settings");
		
		
		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5,5,5,5);
		
		
		gbc.gridx = 0;		
		gbc.gridy = 0;	
		gbc.fill = GridBagConstraints.NONE; 
		gbc.anchor = GridBagConstraints.WEST;  
		gbc.weightx = 1.0;
		
		JLabel title1 = new JLabel("General Settings");
		title1.setFont(new Font("Dialog", Font.BOLD, 20));
		centerPanel.add(title1, gbc);
		
		
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL; 
		
		JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); 
		String[] languages = {"Spanish", "English", "Italian", "German", "Japanese", "Français"};
		
		languagePanel.add(new JLabel("Main Language: "));
		languagePanel.add(new JComboBox<String>(languages));
		centerPanel.add(languagePanel, gbc);
		
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		
		JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));		
		String[] mode = {"Bright", "Dark"};
		
		modePanel.add(new JLabel("Theme: "));
		modePanel.add(new JComboBox<String>(mode));
		centerPanel.add(modePanel, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		
		JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		String[] sortOptions = { "Most Recent", "Oldest", "Title A-Z", "Title Z-A", "Logest Duration", "Shortest Duration"};
		
		sortPanel.add(new JLabel("Preferred Download Filter: "));
		sortPanel.add( new JComboBox<String>(sortOptions));		
		centerPanel.add(sortPanel, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		
		JPanel dataStealPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		JCheckBox dataSteal = new JCheckBox("Let us share your data", true);
		centerPanel.add(dataSteal, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 0.0;
		
		JButton save = new JButton("Save configuration data");
		centerPanel.add(save, gbc);
		
		
		
		
		
		centerPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		add(centerPanel, BorderLayout.CENTER);
	}
	
	

}
