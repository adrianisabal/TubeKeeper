package gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.Insets;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


import gui.MainFrame;

public class Sidebar extends JPanel {

    private final MainFrame mainFrame; 
    private boolean isExpanded = false;
    private List<DefaultButton> menuButtons = new ArrayList<>();
    private DefaultButton expandBut;
    private ImageIcon iconExpand = new ImageIcon("resources/images/menuIcon.png");
    private ImageIcon iconSettings = new ImageIcon("resources/images/settingsIcon.png");
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JLabel progText;

 
    public Sidebar(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(70, 750));
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setMaximumSize(new Dimension(250, 750));

        northPanel = new JPanel();
        northPanel.setOpaque(false);
        northPanel.setLayout(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);

        southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setLayout(new BorderLayout());
        add(southPanel, BorderLayout.SOUTH);

        createButtons();
    }

    private void createButtons() {
        expandedActionListener();

        JSeparator sep = new JSeparator();
        sep.setOpaque(true);
        sep.setPreferredSize(new Dimension(1, 8));
        northPanel.add(sep, BorderLayout.SOUTH);

        String[] names = {"Quick Download", "Playlists Menu", "Downloads"};
        String[] ids   = {MainFrame.VIEW_SEARCH, MainFrame.VIEW_PLAYLISTS, MainFrame.VIEW_DOWNLOADS};

        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 12, 0);

        centerPanel.removeAll();
        centerPanel.setOpaque(false);

        int row = 0;
        for (int i = 0; i < names.length; i++) {
            DefaultButton newButton = new DefaultButton(names[i]);
            if (!isExpanded) {
                newButton.setCollapsed(true);
            }
            applyButtonSize(newButton);

            newButton.setActionCommand(ids[i]);

            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id = ((AbstractButton) e.getSource()).getActionCommand();
                    mainFrame.showScreen(id);
                }
            });

            menuButtons.add(newButton);

            gbc.gridy = row++;
            listPanel.add(newButton, gbc);
        }

        gbc.gridy = row;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        listPanel.add(filler, gbc);

        centerPanel.add(listPanel, BorderLayout.CENTER);

        setSettingsButton();
        createProgText();

        revalidate();
        repaint();
    }

    private void applyButtonSize(DefaultButton b) {
        int height = 40;
        int width = isExpanded ? 210 : 60;
        Dimension pref = new Dimension(width, height);
        b.setPreferredSize(pref);
        b.setMinimumSize(new Dimension(50, height));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
    }

    private void expandedActionListener() {
        expandBut = new DefaultButton(iconExpand, new Dimension(60, 60));
        expandBut.setBorder(null);

        expandBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSidebar();
            }
        });

        northPanel.add(expandBut, BorderLayout.WEST);
    }

    private void setSettingsButton() {
        DefaultButton settingsBut = new DefaultButton(iconSettings);
        settingsBut.setBorder(null);

        if (!isExpanded) {
            settingsBut.setCollapsed(true);
        }

        settingsBut.setActionCommand(MainFrame.VIEW_SETTINGS);
        settingsBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = ((AbstractButton) e.getSource()).getActionCommand();
                mainFrame.showScreen(id);
            }
        });

        menuButtons.add(settingsBut);

        southPanel.add(settingsBut, BorderLayout.CENTER);
        JPanel spacer = new JPanel(null);
        spacer.setOpaque(false);
        southPanel.add(spacer, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void toggleSidebar() {
        isExpanded = !isExpanded;

        if (isExpanded) {
            setPreferredSize(new Dimension(250, 750));
            expandBut.setNewDimension(new Dimension(60, 60));
            expandBut.setIcon(iconExpand);
            if (progText != null) progText.setText("TubeKeeper");
            for (DefaultButton but : menuButtons) {
                but.setCollapsed(false);
                applyButtonSize(but);
            }
        } else {
            setPreferredSize(new Dimension(70, 750));
            expandBut.setNewDimension(new Dimension(60, 60));
            expandBut.setIcon(iconExpand);
            if (progText != null) progText.setText("");
            for (DefaultButton but : menuButtons) {
                but.setCollapsed(true);
                applyButtonSize(but);
            }
        }

        revalidate();
        repaint();
    }

    private void createProgText() {
        progText = new JLabel("");
        progText.setFont(new Font("Monospaced", Font.ITALIC, 15));
        northPanel.add(progText, BorderLayout.EAST);
    }
}
