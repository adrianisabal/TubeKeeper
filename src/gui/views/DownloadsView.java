package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import gui.tools.SearchBar;
import gui.tools.VideoDetailsPanel;
import domain.Video;
import gui.tools.DefaultButton;
import utils.ImageUtils;
import db.VideoDAO;

public class DownloadsView extends View {
	
	private JTable downloadsTable;
	private DefaultTableModel tableModel;
	private TableRowSorter<DefaultTableModel> sorter;
	private VideoDetailsPanel detailsPanel;
  private ArrayList<Video> videos = new ArrayList<>();
	
	public DownloadsView() {
		super(ViewType.MAIN_VIEW, "Downloads");
				
		initTable();
		refreshTable();
		
		setHeader();
		
		JScrollPane tableContainer = new JScrollPane(this.downloadsTable); 	
		
		add(tableContainer, BorderLayout.CENTER);
		
		this.detailsPanel = new VideoDetailsPanel();
		this.detailsPanel.setPreferredSize(new Dimension(500, 250));
		configureDelete();
		add(detailsPanel, BorderLayout.SOUTH);
		
		tableContainer.setBorder(null);
	}
	
	private void setHeader() {
		SearchBar searcher = new SearchBar("Search your video by its title / author", false);
		configureSorter(searcher, this.sorter);

		GridBagConstraints gbc = defineConstraints();
		
		getHeaderPanel().add(searcher, gbc);

		gbc.gridx = 1;
 		gbc.anchor = GridBagConstraints.EAST;

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
	    
		this.downloadsTable = createTable();
		this.downloadsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.sorter = new TableRowSorter<>(tableModel);
		this.downloadsTable.setRowSorter(sorter);
		
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
		
		setRenderer();
	}
	
	private void configureDelete() {
		this.detailsPanel.addDeleteButtonListener(e -> {
		    
		    Video videoToDelete = detailsPanel.getCurrentVideo();

		    if (videoToDelete != null) {
		        int response = javax.swing.JOptionPane.showConfirmDialog(
		            this, 
		            "¿Delete " + videoToDelete.getTitle() + "?",
		            "Confirm",
		            javax.swing.JOptionPane.YES_NO_OPTION
		        );

		        if (response == javax.swing.JOptionPane.YES_OPTION) {
		            // Llamar al DAO, borrar y refrescar...
		            // deleteVideo(videoToDelete);
		            refreshTable();
		            detailsPanel.updateVideoDetails(null);
		        }
		    }
		});
	}
	
	private void setRenderer() {
		
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	centerRenderer.setOpaque(true);
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
	        label.setForeground(table.getForeground());
	    } else {
	        label.setBackground(table.getBackground());
	        label.setForeground(table.getForeground());
	    }
	    
	    if (value instanceof ImageIcon) {
	        ImageIcon originalIcon = (ImageIcon) value;
	        ImageIcon resizedIcon = ImageUtils.resizeImageIcon(originalIcon, 70, 70);
	        label.setIcon(resizedIcon);
	    } else {
	        label.setIcon(null);
	        label.setText(value != null ? value.toString() : "");
	    }
	    
	    return label;
	  }
	  });
	}
	
	  private JTable createTable() {
		  JTable table = new JTable(tableModel) {
			  
		    @Override
		    public String getToolTipText(java.awt.event.MouseEvent e) {
		        java.awt.Point p = e.getPoint();
		        int rowIndex = rowAtPoint(p);
		        int colIndex = columnAtPoint(p);
	
		        rowIndex = convertRowIndexToModel(rowIndex);
		        colIndex = convertColumnIndexToModel(colIndex);
	
		        if (rowIndex < 0 || colIndex < 0) return null;
	
		        if (colIndex == 2 || colIndex == 3) {
		            Object value = getModel().getValueAt(rowIndex, colIndex);
		            return value != null ? value.toString() : null;
		        }
		        return null;
		    }
		  };
		  return table;
	  }
  
	private void configureSorter(SearchBar searchBar, TableRowSorter<DefaultTableModel> sorter) {

		searchBar.setOnEnterAction(text -> {
		
		if (text == null || text.isBlank() || text.equals(searchBar.getPlaceholder())) {
			sorter.setRowFilter(null);
			return;
		}
		
		sorter.setRowFilter(createFilter(text));
		});
		}
	
	
	private RowFilter<DefaultTableModel, Object> createFilter(String text) {

	    return new RowFilter<>() {
	        @Override
	        public boolean include(Entry<? extends DefaultTableModel, ?> entry) {

	            String title  = entry.getStringValue(2);
	            String author = entry.getStringValue(3);

	            String filtro = text.toLowerCase();

	            return title.toLowerCase().contains(filtro)
	                || author.toLowerCase().contains(filtro);
	        }
	    };
	}

	
	  private void loadVideosFromDatabase() {
		  videos.clear();
		  try {
			  VideoDAO dao = new VideoDAO();
			  videos.addAll(dao.findAll());
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }

  
	  public void refreshTable() {
		  
		  loadVideosFromDatabase();
		  tableModel.setRowCount(0);
		  
		  for (Video v : videos) {
			  String sizeString;
		      if (v.getFileSize() > 0) {
		           double sizeInMb = v.getFileSize() / (1024.0 * 1024.0);
		           sizeString = String.format("%.2f MB", sizeInMb);
		      } else {
		           sizeString = "0 MB";
		      }
			  tableModel.addRow(new Object[]{v, v.getThumbnail(), v.getTitle(), v.getAuthor(), sizeString});
		  }
	  }
	  
}
