package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

import domain.Playlist;
import domain.Video;
import utils.ImageUtils;

import gui.MainFrame;
import gui.tools.DefaultButton;
import db.VideoDAO;

public class PlaylistView extends View {
	
  private Playlist playlist;
  private DefaultTableModel playlistDataModel; 
  private JTable playlistTable;
  
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
	    Vector<String> headers = new Vector<>(Arrays.asList("#", "Thumbnail", "Title", "Author", "VIDEO_HIDDEN"));  
      this.playlistDataModel = new DefaultTableModel(new Vector<>(), headers) 
      {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

      List<Video> videos = loadVideosFromDatabase(playlist.getDbId());
	  for (Video v : videos) {
        playlistDataModel.addRow(new Object[]{videos.indexOf(v) + 1, v.getThumbnail(), v.getTitle(), v.getAuthor()}); 
      }
	  
	  JTable playlistTable = new JTable(playlistDataModel);
	  
      playlistTable.setFont(DefaultButton.DEFAULT_FONT);
      playlistTable.getTableHeader().setFont(DefaultButton.DEFAULT_FONT.deriveFont(Font.BOLD));
      
      playlistTable.setRowHeight(80);
      playlistTable.getColumnModel().getColumn(1).setPreferredWidth(120);
      
      playlistTable.getColumnModel().getColumn(4).setMinWidth(0);
      playlistTable.getColumnModel().getColumn(4).setMaxWidth(0);
      playlistTable.getColumnModel().getColumn(4).setPreferredWidth(0);
      
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	
		for (int i = 0; i < playlistTable.getColumnCount(); i++) {
			playlistTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

      playlistTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
        
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
      
      refreshTable();
	}

	  public void refreshTable() {
	      playlistDataModel.setRowCount(0);
	
	      List<Video> videos = loadVideosFromDatabase(this.playlist.getDbId());
	      
	      for (Video v : videos) {
	          playlistDataModel.addRow(new Object[]{
	              videos.indexOf(v) + 1, 
	              v.getThumbnail(), 
	              v.getTitle(), 
	              v.getAuthor(),
	              v 
	          }); 
	      }
	  }
	      
	  private List<Video> loadVideosFromDatabase(int playlistId) {
	    try {
	      VideoDAO dao = new VideoDAO();
	      return dao.findByPlaylistId(playlistId);
	    } catch (Exception e) {
	      e.printStackTrace();
	      return java.util.Collections.emptyList();
	    }
	  }
}
