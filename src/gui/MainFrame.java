package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.tools.Sidebar;
import gui.views.DownloadsView;
import gui.views.JVideoView;
import gui.views.PlaylistMenuView;
import gui.views.SearchView;
import gui.views.SettingsView;
import gui.views.View;

public class MainFrame extends JFrame {

    public static final String VIEW_SEARCH     = "SEARCH";
    public static final String VIEW_DOWNLOADS  = "DOWNLOADS";
    public static final String VIEW_PLAYLISTS  = "PLAYLISTS";
    public static final String VIEW_VIDEO      = "VIDEO";
    public static final String VIEW_SETTINGS   = "SETTINGS";

    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);
    private Map<String, View> views = new HashMap<>();
    private String previous;

    public MainFrame() {

        setSize(1200, 800);
        setTitle("TubeKeeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        Sidebar sidebar = new Sidebar(this);
        add(sidebar, BorderLayout.WEST);

        add(contentPanel, BorderLayout.CENTER);

        views.put(VIEW_SEARCH, new SearchView());
        views.put(VIEW_DOWNLOADS, new DownloadsView());
        views.put(VIEW_VIDEO, new JVideoView());
        views.put(VIEW_PLAYLISTS, new PlaylistMenuView());
        views.put(VIEW_SETTINGS, new SettingsView()); //TODO Crear constructores y la view de settings bien



        contentPanel.add(views.get(VIEW_SEARCH), VIEW_SEARCH);
        contentPanel.add(views.get(VIEW_DOWNLOADS), VIEW_DOWNLOADS);
        contentPanel.add(views.get(VIEW_VIDEO), VIEW_VIDEO);
        contentPanel.add(views.get(VIEW_PLAYLISTS), VIEW_PLAYLISTS);
        contentPanel.add(views.get(VIEW_SETTINGS), VIEW_SETTINGS);

        showScreen(VIEW_SEARCH);
        setVisible(true);
    }

    public void showScreen(String id) {
        cardLayout.show(contentPanel, id);
    }
}
