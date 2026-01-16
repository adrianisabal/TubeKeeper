package gui.tools;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

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
import utils.ImageUtils;

public class VideoDetailsPanel extends JPanel {
	
	private JLabel thumbnailContainer;
	private JTable detailsTable;
	private DefaultTableModel tableModel;
	private JButton btnDelete;
	
	private Video currentVideo;

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
        btnDelete.setMaximumSize(new Dimension(200, 30));
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

        TableColumn propColumn = detailsTable.getColumnModel().getColumn(0);
        propColumn.setPreferredWidth(130);
        propColumn.setMaxWidth(130);
        propColumn.setMinWidth(80);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        propColumn.setCellRenderer(centerRenderer);

        detailsTable.getTableHeader().setVisible(false);

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(scrollPane, BorderLayout.CENTER);

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

    try {
        ImageIcon resized = ImageUtils.resizeImageIcon(video.getThumbnail(), 200, 200);
        thumbnailContainer.setIcon(resized);
        thumbnailContainer.setText(null);
    } catch (Exception e) {
        thumbnailContainer.setIcon(null);
        thumbnailContainer.setText("No Image");
    }

    tableModel.setRowCount(0);
    tableModel.addRow(new Object[]{"Title", video.getTitle()});
    tableModel.addRow(new Object[]{"Author", video.getAuthor()});
    
    String sizeStr = video.getFileSize() > 0 
    		? String.format("%.2f MB", video.getFileSize() / (1024.0 * 1024.0)) 
    		: "Unknown";
    tableModel.addRow(new Object[]{"Size", sizeStr});
    tableModel.addRow(new Object[]{"Author", video.getAuthor()});
    }
    


}
