package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import domain.Playlist;

import gui.MainFrame;

public class PlaylistView extends View {
	
	public PlaylistView() {
		super(ViewType.SUB_VIEW, "PlayList Name");
		
		JLabel playlistName = new JLabel("Playlist Name");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0,0,5,0);
		
		getHeaderPanel().add(playlistName, gbc);
		
		
		Vector<String> tableHeader = new Vector<String>(Arrays.asList("Name","Duration"));
		DefaultTableModel playlistDataModel = new DefaultTableModel(new Vector<Vector<Object>>(), tableHeader);
		
		JTable playlistTable = new JTable(playlistDataModel);
		JScrollPane tableContainer = new JScrollPane(playlistTable);
		
		add(tableContainer, BorderLayout.CENTER);
	}

	public PlaylistView(Playlist playlist) {
	    super(ViewType.SUB_VIEW, playlist != null ? playlist.getTitle() : "Playlist");

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
	        
	    Vector<String> tableHeader = new Vector<>(Arrays.asList("Name", "Duration"));
	    DefaultTableModel playlistDataModel = new DefaultTableModel(new Vector<>(), tableHeader);

	    // TODO haya videos en cada playlist: 
	    // for (Video v : playlist.getVideos()) { playlistDataModel.addRow(new Object[]{v.getTitle(), v.getDuration()}); }

	    JTable playlistTable = new JTable(playlistDataModel);
	    JScrollPane tableContainer = new JScrollPane(playlistTable);

	    add(tableContainer, BorderLayout.CENTER);
	}
}
