package gui.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JOptionPane;

import gui.tools.DefaultButton;
import io.ConfigManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import utils.ImageUtils;

import javax.swing.JFileChooser;

public class SettingsView extends JFrame {

  JCheckBox manualResCheck;
  JSpinner horizontalRes;
  JSpinner verticalRes;
  JButton pathButton;
  JComboBox<String> sortCombo;
  JCheckBox historyCheck;
  JCheckBox demoCheck;

  public SettingsView() {
    super();

    JPanel mainPanel = createSubPanel(null);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    JPanel resPanel = createSubPanel(null);
    resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.Y_AXIS));

    JLabel resTitle = createDefaultJLabel("Window Resolution: ");
    resTitle.setFont(new Font("Dialog", Font.BOLD, 20));
    resPanel.add(resTitle);

    manualResCheck = new JCheckBox();
    manualResCheck.setOpaque(false);
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    manualResCheck.setCursor(handCursor);
    horizontalRes = new JSpinner(new SpinnerNumberModel(1920, 640, 7680, 10));
    horizontalRes.setEnabled(manualResCheck.isSelected());
    horizontalRes.setCursor(handCursor);
    verticalRes = new JSpinner(new SpinnerNumberModel(1080, 360, 4320, 10));
    verticalRes.setEnabled(manualResCheck.isSelected());
    verticalRes.setCursor(handCursor);
    
    manualResCheck.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          horizontalRes.setEnabled(manualResCheck.isSelected());
          verticalRes.setEnabled(manualResCheck.isSelected());
      }
    });

    resPanel.add(createSubPanel(new FlowLayout(FlowLayout.CENTER),
        new JLabel("Manually set window resolution: "),
        manualResCheck));

    resPanel.add(createSubPanel(new FlowLayout(FlowLayout.CENTER),
        horizontalRes,
        new JLabel("x"),
        verticalRes));

    mainPanel.add(resPanel);

    JLabel pathTitle = createDefaultJLabel("Default video download path: ");
    pathTitle.setFont(new Font("Dialog", Font.BOLD, 18));
    mainPanel.add(pathTitle);

    JPanel pathPanel = createSubPanel(new FlowLayout(FlowLayout.CENTER));
    pathButton = new JButton("/path/to/download/folder/", ImageUtils.resizeImageIcon(new ImageIcon("resources/images/folderIcon.png"), 20, 20));
    pathButton.setContentAreaFilled(false);
    pathButton.setOpaque(false);
    pathButton.setCursor(handCursor);
    pathButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           JFileChooser jfc = new JFileChooser();
           jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
           if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
              pathButton.setText(jfc.getSelectedFile().toPath().toString());
           }
        }
    });
    
    pathPanel.add(pathButton);
    mainPanel.add(pathPanel);

    JPanel sortPanel = createSubPanel(new FlowLayout(FlowLayout.CENTER));
		String[] sortOptions = {"Most Recent", "Oldest", "Title A-Z", "Title Z-A", "Longest Duration", "Shortest Duration"};
		
    JLabel sortLabel = createDefaultJLabel("Preferred download sort: ");
    sortLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		sortPanel.add(sortLabel);

		sortCombo = new JComboBox<>(sortOptions);
    sortCombo.setCursor(handCursor);
		sortPanel.add(sortCombo);
    mainPanel.add(sortPanel);

    historyCheck = new JCheckBox();
    historyCheck.setSelected(true);
    historyCheck.setCursor(handCursor);
    historyCheck.setOpaque(false);
    JLabel historyLabel =  createDefaultJLabel("Save download history: ");
    historyLabel.setFont(new Font("Dialog", Font.BOLD, 18));
    mainPanel.add(createSubPanel(new FlowLayout(FlowLayout.CENTER),
       historyLabel,
       historyCheck));

    demoCheck = new JCheckBox();
    demoCheck.setSelected(true);
    demoCheck.setCursor(handCursor);
    demoCheck.setOpaque(false);
    mainPanel.add(createSubPanel(new FlowLayout(FlowLayout.CENTER),
          createDefaultJLabel("Demo Mode (create example data): "),
          demoCheck));

    // TODO: Implement delete history button
/*
    JButton deleteHistory = new DefaultButton("Delete Download History");
    deleteHistory.addActionListener(new ActionListener() {
      
    });
    mainPanel.add();
*/
    DefaultButton saveButton = new DefaultButton("Save current settings");
    saveButton.setFont(new Font("Dialog", Font.BOLD, 14));
    saveButton.setCursor(handCursor);
    saveButton.addActionListener(e -> {
      saveConfig();
      JOptionPane.showMessageDialog(this, 
            "Settings saved successfully", 
            "Saved", 
            JOptionPane.INFORMATION_MESSAGE);
    });
    DefaultButton restoreButton = new DefaultButton("Restore default settings");
    restoreButton.setFont(new Font("Dialog", Font.BOLD, 12));
    restoreButton.setCursor(handCursor);
    restoreButton.addActionListener(e -> {
        ConfigManager cfg = new ConfigManager();
        cfg.setDefaults();
        cfg.save();
        JOptionPane.showMessageDialog(this, 
            "Default settings restored.", 
            "Restored", 
            JOptionPane.INFORMATION_MESSAGE);
        repaint();
    });
    mainPanel.add(createSubPanel(new FlowLayout(FlowLayout.CENTER), 
        saveButton,
        restoreButton));

    mainPanel.setBackground(new Color(255, 255, 255));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    mainPanel.setOpaque(true);

    JPanel panelWrapper = new JPanel(new BorderLayout());
    panelWrapper.add(mainPanel, BorderLayout.NORTH);
    add(new JScrollPane(panelWrapper));

    loadCurrentConfig();

    setVisible(true);
    setTitle("TubeKeeper Settings");
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(mainPanel.getWidth() + 30, mainPanel.getHeight() + 32));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  private JPanel createSubPanel(LayoutManager layoutManager, JComponent ... components) {
    JPanel panel = new JPanel(layoutManager);

    for (JComponent component : components) {
      panel.add(component);
    }
    panel.setOpaque(false);
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.setAlignmentY(Component.TOP_ALIGNMENT);
    return panel;
  }

  private JLabel createDefaultJLabel(String text) {
    JLabel label = new JLabel(text);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setAlignmentY(Component.TOP_ALIGNMENT);
    return label;
  }

  private void loadCurrentConfig() {
    ConfigManager cfg = new ConfigManager();

    Boolean manualRes = cfg.isManualRes();
    if (manualRes != null) {
      manualResCheck.setSelected(manualRes);
    }
    
    Dimension resolution = ImageUtils.parseDimension(cfg.getResolution());
    if (resolution != null) {
      horizontalRes.setValue((int) resolution.getWidth());
      verticalRes.setValue((int)resolution.getHeight());
    }

    String downloadPath = cfg.getDownloadPath();
    if (downloadPath != null) {
      pathButton.setText(downloadPath.toString());
    }

    String sort = cfg.getDefaultSort();
    if (sort != null) {
      sortCombo.setSelectedItem(sort);
    }

    Boolean saveHistory = cfg.isSaveHistory();
    if (saveHistory != null) {
      historyCheck.setSelected(saveHistory);
    }
    
    Boolean demoMode = cfg.isDemoMode();
    if (demoMode != null) {
      demoCheck.setSelected(demoMode);
    }
  }

  private void saveConfig() {
    ConfigManager cfg = new ConfigManager();

    cfg.setManualRes(manualResCheck.isSelected());
    cfg.setResolution(new Dimension((int)horizontalRes.getValue(), (int) verticalRes.getValue()));
    cfg.setDownloadPath(pathButton.getText());
    cfg.setDefaultSort((String) sortCombo.getSelectedItem());
    cfg.setSaveHistory(historyCheck.isSelected());
    cfg.setDemoMode(demoCheck.isSelected());

    cfg.save();
  }
}
