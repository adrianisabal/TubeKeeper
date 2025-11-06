package gui.views;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import domain.Playlist;
import utils.ImageUtils;

public class PlaylistMenuView extends View {
  private ArrayList<Playlist> playlists = new ArrayList<>(Arrays.asList(new Playlist("Playlist 1", new ImageIcon("resources/images/logo.png")), new Playlist("Playlist 2", new ImageIcon("resources/images/logo.png")), new Playlist("Playlist 3", new ImageIcon("resources/images/logo.png"))));

  public PlaylistMenuView() {
    super(ViewType.MAIN_VIEW, "PlaylistMenuView");
    JPanel mainPanel = new JPanel();
    for (Playlist p : playlists) {
      JPanel plPanel = new JPanel(new GridLayout(3, 1, 20, 20));
      plPanel.add(new JLabel(ImageUtils.resizeImageIcon(p.getThumbnail(), 150, 150)));
      plPanel.add(new JLabel(p.getTitle()));
      plPanel.add(new JLabel(p.getAuthor()));
      mainPanel.add(plPanel);
    }

    JScrollPane plScrollPanel = new JScrollPane(mainPanel);
    this.add(plScrollPanel);
  }
}
