package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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

}
