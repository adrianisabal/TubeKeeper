package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

import domain.Playlist;
import domain.Video;
import utils.ImageUtils;

import gui.MainFrame;

public class PlaylistView extends View {
	
  private Playlist playlist;
	
  public PlaylistView(Playlist playlist) {
	    super(ViewType.SUB_VIEW, playlist.getTitle());
    
      this.playlist = playlist;

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridy = 2;
	    gbc.anchor = GridBagConstraints.WEST;
	    gbc.insets = new Insets(0, 0, 5, 0);

	    if (playlist != null && playlist.getAuthor() != null) {
	      JLabel subtitle = new JLabel("by " + playlist.getAuthor());
	      getHeaderPanel().add(subtitle, gbc);
	    }

	    getBackButton().addActionListener(e -> {
	      Window w = SwingUtilities.getWindowAncestor(this);
	      if (w instanceof MainFrame) {
	        ((MainFrame) w).showScreen(MainFrame.VIEW_PLAYLISTS);
	      }
	    });
	    
      DefaultTableModel playlistDataModel = new DefaultTableModel(new Vector<>(), new Vector<String>(Arrays.asList("#", "Thumbnail", "Title", "Author")));

   
      generateVideos();    

	    for (Video v : playlist.getVideos()) {
        playlistDataModel.addRow(new Object[]{playlist.getVideos().indexOf(v) + 1, v.getThumbnail(), v.getTitle(), v.getAuthor()}); 
      }

	    JTable playlistTable = new JTable(playlistDataModel);
      

      JPanel plPanel = new JPanel(new BorderLayout());
    
      JLabel imageLabel = new JLabel(ImageUtils.resizeImageIcon(playlist.getThumbnail(), 400, 400));
      imageLabel.setHorizontalAlignment(JLabel.CENTER);
      plPanel.add(imageLabel, BorderLayout.CENTER);
    
      JPanel textPanel = new JPanel(new GridLayout(2, 1));
      textPanel.add(new JLabel(playlist.getTitle(), JLabel.CENTER));
      textPanel.add(new JLabel(playlist.getAuthor(), JLabel.CENTER));
      plPanel.add(textPanel, BorderLayout.SOUTH);

	    JScrollPane tableContainer = new JScrollPane(playlistTable);

      add(plPanel, BorderLayout.WEST);
	    add(tableContainer, BorderLayout.CENTER);
	}

  private void generateVideos() {
    for (int i = 0; i < 20; i++) {
      playlist.getVideos().add(new Video("Title" + i, "Author" + i, new ImageIcon("resources/images/logo.png")));
    }
  }
}
