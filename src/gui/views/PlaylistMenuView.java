package gui.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import domain.Playlist;
import utils.ImageUtils;

public class PlaylistMenuView extends View {
  
  private ArrayList<Playlist> playlists = new ArrayList<>();

  public PlaylistMenuView() {
    super(ViewType.MAIN_VIEW, "PlaylistMenuView");
    generatePlaylists();
    JPanel mainPanel = new JPanel(new GridLayout((int) (Math.ceil(playlists.size()/3.0)), 3, 20, 20));
    for (Playlist p : playlists) {
      JPanel plPanel = new JPanel(new BorderLayout());
    
      JLabel imageLabel = new JLabel(ImageUtils.resizeImageIcon(p.getThumbnail(), 150, 150));
      imageLabel.setHorizontalAlignment(JLabel.CENTER);
      plPanel.add(imageLabel, BorderLayout.CENTER);
    
      JPanel textPanel = new JPanel(new GridLayout(2, 1));
      textPanel.add(new JLabel(p.getTitle(), JLabel.CENTER));
      textPanel.add(new JLabel(p.getAuthor(), JLabel.CENTER));
    
      plPanel.add(textPanel, BorderLayout.SOUTH);
      mainPanel.add(plPanel);
    }
    JScrollPane plScrollPanel = new JScrollPane(mainPanel);
    this.add(plScrollPanel);
  }

  private void generatePlaylists() {
     for (int i = 0; i < 20; i++) {
      this.playlists.add(new Playlist("Playlist " + i, new ImageIcon("resources/images/logo.png")));
    }
  }
}
