package gui.tools;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import domain.Video;
import io.ConfigManager;
import utils.ImageUtils;

public class VideoDetailsPanel extends JPanel {
	
	private JLabel thumbnailContainer;
	private JTable detailsTable;
	private DefaultTableModel tableModel;
	private JButton btnDelete;
	
	private Video currentVideo;
	private String currentVideoUrl = "";

    public VideoDetailsPanel() {
    	setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(this.getBackground()); 
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        
        thumbnailContainer = new JLabel();
        thumbnailContainer.setPreferredSize(new Dimension(200, 120));
        thumbnailContainer.setAlignmentX(CENTER_ALIGNMENT);
        
        btnDelete = new DefaultButton("Delete Video");

        btnDelete.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnDelete.setOpaque(false);
        btnDelete.setMaximumSize(new Dimension(150, 25));
        btnDelete.setAlignmentX(CENTER_ALIGNMENT);
        btnDelete.setVisible(false);
        
        leftPanel.add(thumbnailContainer);
        leftPanel.add(Box.createVerticalStrut(12)); 
        leftPanel.add(btnDelete);
        
        add(leftPanel, BorderLayout.WEST);
        
        createDetailsTable();
    }
    
    private void createDetailsTable() {
        String[] columns = {"Property", "Value"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        detailsTable = new JTable(tableModel);
        detailsTable.setRowHeight(24);
        detailsTable.setShowGrid(false);
        detailsTable.setFocusable(false);
        detailsTable.setRowSelectionAllowed(false);
        detailsTable.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        configureRenderer();
        detailsTable.getTableHeader().setVisible(false);
        configureLinkListener();

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(scrollPane, BorderLayout.CENTER);

    }
    
    private void configureRenderer() {
        TableColumn col0 = detailsTable.getColumnModel().getColumn(0);
        col0.setPreferredWidth(130);
        col0.setMaxWidth(130);
        col0.setMinWidth(80);
     
        Color colorPar = new Color(245, 245, 245);
        Color colorImpar = new Color(240, 240, 240); 

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                	if (row % 2 == 0) { 
                		c.setBackground(colorPar);
                	} else {
                		c.setBackground(colorImpar);
            		}
                } 
                if (column == 0) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                	setHorizontalAlignment(SwingConstants.LEFT);
                }
                
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

                return c;
            }
        };

        col0.setCellRenderer(renderer);
        detailsTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
        detailsTable.setIntercellSpacing(new Dimension(0, 0)); 
    }
    
    public void addDeleteButtonListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }
    
    public Video getCurrentVideo() {
        return currentVideo;
    }

    public void updateVideoDetails(Video video) {
    	this.currentVideo = video;
    	
        if (video == null) {
        	thumbnailContainer.setIcon(null);
        	thumbnailContainer.setText("");
        	tableModel.setRowCount(0);
        	btnDelete.setVisible(false);
            return;
        }
        
        btnDelete.setVisible(true);
        currentVideoUrl = video.getUrl(); // ya no la usaremos para abrir, pero la dejamos por si se quiere mostrar

        try {
            ImageIcon resized = ImageUtils.resizeImageIcon(video.getThumbnail(), 200, 200);
            thumbnailContainer.setIcon(resized);
            thumbnailContainer.setText(null);
        } catch (Exception e) {
            thumbnailContainer.setIcon(null);
            thumbnailContainer.setText("No Image");
        }

        tableModel.setRowCount(0);

        String durationStr = video.formatDuration(video.length()); 
        String sizeStr = video.formatSize(video.getFileSize());
        String viewsStr = video.formatViews(video.getViews());
        
        // Título se muestra como “link”
        String titleDisplay = "<html><font color='blue'><u>" + video.getTitle() + "</u></font></html>";
        
        tableModel.addRow(new Object[]{"Title", titleDisplay});
        tableModel.addRow(new Object[]{"Author", video.getAuthor()});
        tableModel.addRow(new Object[]{"Published", video.getPublishDate()});
        tableModel.addRow(new Object[]{"Duration", durationStr});
        tableModel.addRow(new Object[]{"Views", viewsStr});
        tableModel.addRow(new Object[]{"Size", sizeStr});
    }
    
    
    private void configureLinkListener() {
    	detailsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = detailsTable.rowAtPoint(e.getPoint());
                int col = detailsTable.columnAtPoint(e.getPoint());
                
                // Antes: abría la URL remota con openWebpage(currentVideoUrl)
                // Ahora: abrimos el archivo local final con el reproductor por defecto
                if (row == 0 && col == 1 && currentVideo != null) {
                    openLocalFile(currentVideo);
                }
            }
    	});
    }

    /**
     * Abre el fichero local asociado al vídeo (mp4 o mp3) con el reproductor por defecto.
     * Se basa en la ruta de descarga configurada en ConfigManager y en el título del vídeo,
     * igual que se hace en DownloadsView.deletePhysicalFile.
     */
    private void openLocalFile(Video video) {
        try {
            ConfigManager cfg = new ConfigManager();
            String baseDownloadPath = cfg.getDownloadPath();

            if (baseDownloadPath == null || baseDownloadPath.isBlank()) {
                System.err.println("Download path not configured");
                return;
            }

            String safeTitle = video.getTitle();
            File mp4 = new File(baseDownloadPath + File.separator + safeTitle + ".mp4");
            File mp3 = new File(baseDownloadPath + File.separator + safeTitle + ".mp3");

            File target = null;
            if (mp4.exists()) {
                target = mp4;
            } else if (mp3.exists()) {
                target = mp3;
            }

            if (target == null) {
                System.err.println("Local file not found for video: " + video.getTitle());
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Local file not found:\n" + safeTitle + ".mp4 / .mp3",
                        "File not found",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(target);
                return;
            }

            // Fallback según SO
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder builder;

            if (os.contains("win")) {
                builder = new ProcessBuilder("cmd", "/c", "start", "", target.getAbsolutePath());
            } else if (os.contains("mac")) {
                builder = new ProcessBuilder("open", target.getAbsolutePath());
            } else {
                builder = new ProcessBuilder("xdg-open", target.getAbsolutePath());
            }

            builder.start();

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Could not open the video file.\nError: " + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // openWebpage ya no se usa para el título, pero lo dejo por si lo quieres en otro sitio
    private void openWebpage(String urlString) {
        try {
            if (Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(urlString));
                return;
            }
        } catch (Exception e) {
            System.err.println("Desktop API failed, switching to ProcessBuilder...");
        }

        String os = System.getProperty("os.name").toLowerCase();

        try {
            ProcessBuilder builder = null;

            if (os.contains("win")) {
                builder = new ProcessBuilder(
                        "rundll32", "url.dll,FileProtocolHandler", urlString);
            } else if (os.contains("mac")) {
                builder = new ProcessBuilder("open", urlString);
            } else if (os.contains("nix") || os.contains("nux")) {
                String[] browsers = {
                        "xdg-open", "google-chrome", "firefox", "mozilla"
                };

                for (String browser : browsers) {
                    try {
                        new ProcessBuilder(browser, urlString).start();
                        return;
                    } catch (IOException ignored) {}
                }
            }

            if (builder != null) {
                builder.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Could not open browser.\nError: " + e.getMessage()
            );
        }
    }
}
