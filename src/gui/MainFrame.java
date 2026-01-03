package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.BorderFactory;

import java.awt.CardLayout;
import java.awt.BorderLayout;

import java.util.HashMap;
import java.util.Map;

import domain.Playlist;
import gui.views.View;
import gui.views.SearchView;
import gui.tools.Sidebar;
import gui.views.DownloadsView;
import gui.views.PlaylistMenuView;
import gui.views.PlaylistView;
import gui.views.JVideoView;
import gui.views.SettingsView;

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

    private SettingsView settingsView;

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
        views.put(VIEW_VIDEO, new JVideoView());
        views.put(VIEW_PLAYLISTS, new PlaylistMenuView());

        contentPanel.add(views.get(VIEW_SEARCH), VIEW_SEARCH);
        contentPanel.add(views.get(VIEW_VIDEO), VIEW_VIDEO);
        contentPanel.add(views.get(VIEW_PLAYLISTS), VIEW_PLAYLISTS);

        showScreen(VIEW_SEARCH);
        setVisible(true);
    }

    public void showScreen(String id) {
        if (VIEW_SETTINGS.equals(id)) {
            if (settingsView == null || !settingsView.isVisible()) {
                settingsView = new SettingsView();
                settingsView.setLocationRelativeTo(null);
            }
            settingsView.setVisible(true);
            settingsView.toFront();
            return;
        }
        if (!views.containsKey(id)) {
            if (VIEW_DOWNLOADS.equals(id)) {
                View v = new DownloadsView();
                views.put(id, v);
                contentPanel.add(v, id);
            } else if (VIEW_PLAYLISTS.equals(id)) {
                View v = new PlaylistMenuView();
                views.put(id, v);
                contentPanel.add(v, id);
            }
        } else {
            if (VIEW_PLAYLISTS.equals(id)) {
                View v = new PlaylistMenuView();
                views.put(id, v);
                contentPanel.add(v, id);
            }
        }
        if (VIEW_DOWNLOADS.equals(id) && views.get(id) instanceof DownloadsView) {
            ((DownloadsView) views.get(id)).refreshTable();
        }
        cardLayout.show(contentPanel, id);
    }
    
    public void showPlaylist(Playlist playlist) {
        String id = "PLAYLIST:" + System.identityHashCode(playlist);

        if (!views.containsKey(id)) {
            PlaylistView pv = new PlaylistView(playlist);
            views.put(id, pv);
            contentPanel.add(pv, id);
        }

        previous = VIEW_PLAYLISTS;
        showScreen(id);
    }
}
