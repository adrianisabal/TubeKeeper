package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gui.tools.SearchBar;

public class DownloadsView extends View {
	
	public DownloadsView() {
		super(ViewType.MAIN_VIEW, "Downloads");
				
		JTable downloadsTable = initTable();
		JScrollPane tableContainer = new JScrollPane(downloadsTable);
		
		SearchBar searcher = new SearchBar("Search your video by it's title.");
		
		GridBagConstraints gbc = defineConstraints();
	
		getHeaderPanel().add(searcher, gbc);

		gbc.gridx = 1;
 		gbc.anchor = GridBagConstraints.EAST;
 		
		String[] sortOptions = { "Most Recent", "Oldest", "Title A-Z", "Title Z-A", "Logest Duration", "Shortest Duration"};
		getHeaderPanel().add(new JComboBox<String>(sortOptions), gbc);
		
		add(tableContainer, BorderLayout.CENTER);
		
		tableContainer.setBorder(null);
	}
	
	
	private GridBagConstraints defineConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
 		gbc.gridy = 1;
 		gbc.anchor = GridBagConstraints.CENTER;
 		gbc.insets = new Insets(0, 5, 15, 0);
	
 		return gbc;
	}
	
	
	private JTable initTable() {
		
		Vector<String> header = new Vector<String>(Arrays.asList("Time", "Title", "Album", "Duration", "Size"));
		DefaultTableModel dataModel = new DefaultTableModel(new Vector<Vector<Object>>(), header);
		
		return new JTable(dataModel);
	}
}
