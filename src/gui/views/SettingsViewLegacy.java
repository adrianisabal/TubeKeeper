package gui.views;

/*
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

import io.ConfigManager;

public class SettingsViewLegacy extends View{

  private JComboBox<String> languageCombo;
  private JComboBox<String> themeCombo;
  private JComboBox<String> sortCombo;
  private JCheckBox shareDataCheck;

	public SettingsViewLegacy() {
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
		languageCombo = new JComboBox<>(languages);
		languagePanel.add(languageCombo);
		centerPanel.add(languagePanel, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		
		JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));		
		String[] mode = {"Bright", "Dark"};
		
		modePanel.add(new JLabel("Theme: "));
		themeCombo = new JComboBox<>(mode);
		modePanel.add(themeCombo);
		centerPanel.add(modePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		
		JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		String[] sortOptions = { "Most Recent", "Oldest", "Title A-Z", "Title Z-A", "Logest Duration", "Shortest Duration"};
		
		sortPanel.add(new JLabel("Preferred Download Filter: "));
		sortCombo = new JComboBox<>(sortOptions);
		sortPanel.add(sortCombo);		
		centerPanel.add(sortPanel, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		
		shareDataCheck = new JCheckBox("Do you like this app?", false);
		centerPanel.add(shareDataCheck, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 0.0;
		
		JButton save = new JButton("Save configuration data");
		centerPanel.add(save, gbc);

    loadCurrentConfig();

    save.addActionListener(e -> saveConfig());
		
		centerPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		add(centerPanel, BorderLayout.CENTER);
	}

  private void loadCurrentConfig() {
    ConfigManager cfg = new ConfigManager();

    String lang = cfg.getLanguage();
    if (lang != null) {
      languageCombo.setSelectedItem(lang);
    }

    String theme = cfg.getTheme();
    if (theme != null) {
      themeCombo.setSelectedItem(theme);
    }

    String sort = cfg.getDefaultSort();
    if (sort != null) {
      sortCombo.setSelectedItem(sort);
    }

    shareDataCheck.setSelected(cfg.isShareData());
  }

  private void saveConfig() {
    ConfigManager cfg = new ConfigManager();

    String language = (String) languageCombo.getSelectedItem();
    String theme = (String) themeCombo.getSelectedItem();
    String sort = (String) sortCombo.getSelectedItem();
    boolean shareData = shareDataCheck.isSelected();

    cfg.setLanguage(language);
    cfg.setTheme(theme);
    cfg.setDefaultSort(sort);
    cfg.setShareData(shareData);
    cfg.save();
  }
}
*/
