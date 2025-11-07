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
	    	Youtube ep1 = new Youtube("https://www.youtube.com/watch?v=icK5DN0MLPc");  
	    	addDownload(ep1, "Vídeo 1: Planeta Vegetta #1", "20:07", "80 MB");  // vídeo de Vegetta777 

	    	Youtube ep2 = new Youtube("https://www.youtube.com/watch?v=OuIixcOsl9M");  
	    	addDownload(ep2, "Vídeo 2: Finalmente os respondo", "22:03", "90 MB");  // vídeo de Willyrex 

	    	Youtube ep3 = new Youtube("https://www.youtube.com/watch?v=u9-n0HtolxM");  
	    	addDownload(ep3, "Vídeo 3: 100 días en Apocalipsis Minecraft", "21:32", "85 MB");  // vídeo de Vegetta777 

	    	Youtube ep4 = new Youtube("https://www.youtube.com/watch?v=i9eI9Hcwc4s");  
	    	addDownload(ep4, "Vídeo 4: Mi Primera Vez en Rubius SAW Game", "43:24", "110 MB");  // vídeo de El Rubius 

	    	Youtube ep5 = new Youtube("https://www.youtube.com/watch?v=zpUnj3lnG2g");  
	    	addDownload(ep5, "Vídeo 5: Willy vs Lucky Blocks de Sonic", "11:02", "40 MB");  // vídeo de Willyrex 

	    	Youtube ep6 = new Youtube("https://www.youtube.com/watch?v=ii-gbjflK-8");	// Marin C
	    	addDownload(ep6, "Vídeo 6:  Sketch Debate Microsoft VS Linux Semana ESIDE ", "2:34", "28 MB");

	    	Youtube ep7 = new Youtube("https://www.youtube.com/watch?v=vgu9I30du_A");  // ElRichMC
	    	addDownload(ep7, "Vídeo 7: ElRichMC – SHOCK ABSOLUTO", "19:53", "45 MB");

	    	Youtube ep8 = new Youtube("https://www.youtube.com/watch?v=EOjAPm6yI9Q");  // IlloJuan
	    	addDownload(ep8, "Vídeo 8: IlloJuan – LA ETAPA INTELIGENTE", "27:55", "56 MB"); 
	    	
	    	Youtube ep9 = new Youtube("https://www.youtube.com/watch?v=oXmVFUMbdGo");  // M1xwell
	    	addDownload(ep9, "Vídeo 9: M1xwell – FRACTURE JETT GAMEPLAY", "12:12", "12 MB");  

	    	Youtube ep10 = new Youtube("https://www.youtube.com/watch?v=P6vvkcL4bN0");  // M1xwell
	    	addDownload(ep10, "Vídeo 10: M1xwell – JETT BREEZE GAMEPLAY", "33:12", "28 MB"); 



	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this,
	            "Error al crear los objetos Youtube:\n" + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	//
}
