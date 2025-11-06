package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.github.felipeucelli.javatube.Youtube;

import gui.tools.SearchBar;
import gui.tools.VideoDetailsPanel;

public class DownloadsView extends View {
	
	private JTable downloadsTable;
	private DefaultTableModel tableModel;
	private VideoDetailsPanel detailsPanel;
	
	public DownloadsView() {
		super(ViewType.MAIN_VIEW, "Downloads");
				
		setHeader();
		
		initTable();
		addExampleDownloads();
		
		JScrollPane tableContainer = new JScrollPane(this.downloadsTable); 		
		add(tableContainer, BorderLayout.CENTER);
		
		this.detailsPanel = new VideoDetailsPanel();
		add(detailsPanel, BorderLayout.SOUTH);
		
		tableContainer.setBorder(null);
	}
	
	private void setHeader() {
		SearchBar searcher = new SearchBar("Search your video by its title");
		GridBagConstraints gbc = defineConstraints();
		
		getHeaderPanel().add(searcher, gbc);

		gbc.gridx = 1;
 		gbc.anchor = GridBagConstraints.EAST;
 		
		String[] sortOptions = { "Most Recent", "Oldest", "Title A-Z", "Title Z-A", "Logest Duration", "Shortest Duration"};
		getHeaderPanel().add(new JComboBox<String>(sortOptions), gbc);

	}
	
	private GridBagConstraints defineConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
 		gbc.gridy = 1;
 		gbc.anchor = GridBagConstraints.CENTER;
 		gbc.insets = new Insets(0, 5, 15, 0);
	
 		return gbc;
	}
	
	
	private void initTable() {
		
		Vector<String> header = new Vector<String>(Arrays.asList("Video", "Title", "Playlist", "Duration", "Size"));
		this.tableModel = new DefaultTableModel(new Vector<Vector<Object>>(), header) {
	        @Override
	        public boolean isCellEditable(int row, int col) {
	            return false; 
	        }
	    };
		
		this.downloadsTable = new JTable(this.tableModel);
		this.downloadsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumn hiddenCol = this.downloadsTable.getColumnModel().getColumn(0);
		hiddenCol.setWidth(0);
		hiddenCol.setMinWidth(0);
		hiddenCol.setMaxWidth(0);
		
		this.downloadsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = downloadsTable.getSelectedRow();
				Youtube video = (Youtube) downloadsTable.getValueAt(row, 0);
				detailsPanel.updateVideoDetails(video);
			}
		});
	}
	
	
	private void addDownload(Youtube v, String playlistName, String duration, String size) {
		String videoTitle = new String();
		
		try {
			videoTitle = v.getTitle();
		} catch (Exception e) {
			videoTitle = "Video Title";
		}
		
		this.tableModel.addRow(new Vector<Object>(Arrays.asList(v, videoTitle, playlistName, duration, size)));
	}
	

	
	// CHAT-GPT
	private void addExampleDownloads() {
	    try {
	        Youtube v1 = new Youtube("https://www.youtube.com/watch?v=5qap5aO4i9A"); // lo-fi hip hop radio
	        addDownload(v1, "Chill Vibes", "01:12:33", "120 MB");

	        Youtube v2 = new Youtube("https://www.youtube.com/watch?v=2OEL4P1Rz04"); // NCS - Alan Walker - Fade
	        addDownload(v2, "Code Academy", "15:45", "45 MB");

	        Youtube v3 = new Youtube("https://www.youtube.com/watch?v=1vPPR9tPqA0"); // Chillhop Essentials
	        addDownload(v3, "RetroSound", "59:21", "98 MB");

	        Youtube v4 = new Youtube("https://www.youtube.com/watch?v=2HiKc0xNY1I"); // Relaxing piano music
	        addDownload(v4, "Peaceful Tunes", "03:05:20", "210 MB");

	        Youtube v5 = new Youtube("https://www.youtube.com/watch?v=4q6UGCyHZCI"); // Cooking background music
	        addDownload(v5, "Chef Master", "07:32", "36 MB");

	        Youtube v6 = new Youtube("https://www.youtube.com/watch?v=J2X5mJ3HDYE"); // NCS - Electro-Light
	        addDownload(v6, "Top Tracks", "01:45:10", "180 MB");

	        Youtube v7 = new Youtube("https://www.youtube.com/watch?v=VtF2AgFSLAw"); // Guitar cover
	        addDownload(v7, "Acoustic Vibes", "06:12", "25 MB");

	        Youtube v8 = new Youtube("https://www.youtube.com/watch?v=DWcJFNfaw9c"); // Study/Focus music
	        addDownload(v8, "StudyZone", "02:30:40", "155 MB");

	        Youtube v9 = new Youtube("https://www.youtube.com/watch?v=tgbNymZ7vqY"); // Funny cats
	        addDownload(v9, "Random Clips", "12:04", "50 MB");

	        Youtube v10 = new Youtube("https://www.youtube.com/watch?v=V-_O7nl0Ii0"); // News theme (creative commons)
	        addDownload(v10, "Daily World", "09:23", "60 MB");

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this,
	            "Error al crear los objetos Youtube:\n" + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	//
}
