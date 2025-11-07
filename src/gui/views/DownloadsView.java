package gui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import gui.tools.SearchBar;
import gui.tools.VideoDetailsPanel;
import domain.Video;
import gui.tools.DefaultButton;
import utils.ImageUtils;

public class DownloadsView extends View {
	
	private JTable downloadsTable;
	private DefaultTableModel tableModel;
	private VideoDetailsPanel detailsPanel;
  private ArrayList<Video> videos = new ArrayList<>();
	
	public DownloadsView() {
		super(ViewType.MAIN_VIEW, "Downloads");
				
		setHeader();
		
		initTable();
		addExampleDownloads();

    for (Video v : videos) {
        tableModel.addRow(new Object[]{v, v.getThumbnail(), v.getTitle(), v.getAuthor(), (int) (Math.random()*512) + "MB"}); 
    }
		
		JScrollPane tableContainer = new JScrollPane(this.downloadsTable); 	
		
		add(tableContainer, BorderLayout.CENTER);
		
		this.detailsPanel = new VideoDetailsPanel();
		this.detailsPanel.setPreferredSize(new Dimension(500, 250));
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
		
		Vector<String> header = new Vector<String>(Arrays.asList("Video" , "Thumbnail", "Title", "Author", "Size"));
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
				Video video = (Video) downloadsTable.getValueAt(row, 0);
				detailsPanel.updateVideoDetails(video);
			}
		});
		
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	for (int i = 1; i < downloadsTable.getColumnCount(); i++) {
		downloadsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}
	
		
	downloadsTable.setFont(DefaultButton.DEFAULT_FONT);
	downloadsTable.getTableHeader().setFont(DefaultButton.DEFAULT_FONT.deriveFont(Font.BOLD));
	downloadsTable.setRowHeight(80);
	downloadsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
	downloadsTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
	  
	  @Override  
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    JLabel label = new JLabel();
	    label.setBackground(table.getSelectionBackground());
	    label.setForeground(table.getSelectionForeground());
	    label.setOpaque(true);
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setVerticalAlignment(JLabel.CENTER); 
	
	    if (isSelected) {
	        label.setBackground(table.getSelectionBackground());
	        label.setForeground(table.getSelectionForeground());
	    } else {
	        label.setBackground(table.getBackground());
	        label.setForeground(table.getForeground());
	    }
	    if (value instanceof ImageIcon) {
	        ImageIcon originalIcon = (ImageIcon) value;
	        ImageIcon resizedIcon = ImageUtils.resizeImageIcon(originalIcon, 70, 70);
	        label.setIcon(resizedIcon);
	        label.setText(""); 
	    } else {
	        label.setIcon(null);
	        label.setText(value != null ? value.toString() : "");
	    }
	      
	    return label;
	  }
	  });
	}
	  private void addExampleDownloads() {
	    for (int i = 0; i < 20; i++) {
	      videos.add(new Video("Title" + i, "Author" + i, new ImageIcon("resources/images/logo.png")));
	       
	    }
	  }
	}
