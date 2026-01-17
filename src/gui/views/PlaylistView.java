package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.Font;
import java.awt.Component;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import domain.Playlist;
import domain.Video;
import utils.ImageUtils;
import utils.TubeUtils;
import gui.MainFrame;
import gui.tools.DefaultButton;
import db.VideoDAO;
import io.ConfigManager;

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
      this.playlistDataModel = new DefaultTableModel(new Vector<>(), headers) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

      List<Video> videos = loadVideosFromDatabase(playlist.getDbId());
	  for (Video v : videos) {
        playlistDataModel.addRow(new Object[]{videos.indexOf(v) + 1, v.getThumbnail(), v.getTitle(), v.getAuthor(), v}); 
      }
	  
	  playlistTable = new JTable(playlistDataModel);
	  
      playlistTable.setFont(DefaultButton.DEFAULT_FONT);
      playlistTable.getTableHeader().setFont(DefaultButton.DEFAULT_FONT.deriveFont(Font.BOLD));
      
      playlistTable.setRowHeight(80);
      playlistTable.getColumnModel().getColumn(1).setPreferredWidth(120);
      
      // Ocultamos la columna VIDEO_HIDDEN
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

      playlistTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
              JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
              label.setHorizontalAlignment(SwingConstants.CENTER);
              label.setForeground(Color.BLUE.darker());
              label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
              return label;
          }
      });

      playlistTable.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              int row = playlistTable.rowAtPoint(e.getPoint());
              int col = playlistTable.columnAtPoint(e.getPoint());
              if (row < 0) return;

              if (col == 2) {
                  int modelRow = playlistTable.convertRowIndexToModel(row);
                  Video v = (Video) playlistDataModel.getValueAt(modelRow, 4);
                  openLocalFileFromPlaylist(v);
              }
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

	private void openLocalFileFromPlaylist(Video video) {
	    try {
	        ConfigManager cfg = new ConfigManager();
	        String base = cfg.getDownloadPath();
	        if (base == null || base.isBlank()) {
	            System.err.println("Download path not configured");
	            return;
	        }

	        String playlistFolder = TubeUtils.sanitizeFilename(playlist.getTitle());
	        File playlistDir = new File(base + File. separator + playlistFolder);
	        
	        String safeTitle = TubeUtils.sanitizeFilename(video. getTitle());
	        File mp4 = new File(playlistDir, safeTitle + ".mp4");
	        File mp3 = new File(playlistDir, safeTitle + ".mp3");

	        if (!mp4.exists() && !mp3.exists()) {
	            mp4 = new File(base + File.separator + safeTitle + ".mp4");
	            mp3 = new File(base + File.separator + safeTitle + ".mp3");
	        }

	        File target = null;
	        if (mp4.exists()) {
	            target = mp4;
	        } else if (mp3.exists()) {
	            target = mp3;
	        }

	        if (target != null && target.exists()) {
	            if (java.awt.Desktop.isDesktopSupported()) {
	                java.awt.Desktop.getDesktop().open(target);
	            }
	        } else {
	            JOptionPane.showMessageDialog(this,
	                "Local file not found:\n" + safeTitle + ".mp4 / .mp3",
	                "File not found",
	                JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this,
	            "Error opening file: " + e.getMessage(),
	            "Error",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
}

