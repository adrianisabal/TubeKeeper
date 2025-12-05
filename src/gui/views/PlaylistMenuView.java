package gui.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

import domain.Playlist;
import gui.MainFrame;
import utils.ImageUtils;
import db.PlaylistDAO;

public class PlaylistMenuView extends View {
  
  private ArrayList<Playlist> playlists = new ArrayList<>();

  public PlaylistMenuView() {
    super(ViewType.MAIN_VIEW, "Playlists");
    loadPlaylistsFromDatabase();

    JPanel mainPanel = new JPanel(new GridLayout((int) (Math.ceil(playlists.size()/3.0)), 3, 20, 20));
    for (Playlist p : playlists) {
      JPanel plPanel = new JPanel(new BorderLayout());
    
      JLabel imageLabel = new JLabel(ImageUtils.resizeImageIcon(p.getThumbnail(), 150, 150));
      imageLabel.setHorizontalAlignment(JLabel.CENTER);
      plPanel.add(imageLabel, BorderLayout.CENTER);
    
      JPanel textPanel = new JPanel(new GridLayout(2, 1));
      textPanel.add(new JLabel(p.getTitle(), JLabel.CENTER));
      textPanel.add(new JLabel(p.getAuthor(), JLabel.CENTER));
    
      addMouseAdapter(plPanel, textPanel, p);

      plPanel.add(textPanel, BorderLayout.SOUTH);
      mainPanel.add(plPanel);
    }
    JScrollPane plScrollPanel = new JScrollPane(mainPanel);
    plScrollPanel.getVerticalScrollBar().setUnitIncrement(15);
    plScrollPanel.setBorder(null);
    this.add(plScrollPanel);

  }

  private void loadPlaylistsFromDatabase() {
    playlists.clear();
    try {
      PlaylistDAO dao = new PlaylistDAO();
      playlists.addAll(dao.findAll());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void addMouseAdapter(JPanel plPanel, JPanel textPanel, Playlist playlist) {
    Color originalBg = plPanel.getBackground();
    Color originalTextBg = textPanel.getBackground();
    
    plPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            plPanel.setBackground(new Color(200, 200, 200));
            textPanel.setBackground(new Color(200, 200, 200));
            plPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            plPanel.setBackground(originalBg);
            textPanel.setBackground(originalTextBg);
            plPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            Window w = SwingUtilities.getWindowAncestor(plPanel);
            if (w instanceof MainFrame) {
              ((MainFrame) w).showPlaylist(playlist);
            }
        }
    });
  } 
}
