package gui.tools;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URI;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.github.felipeucelli.javatube.Youtube;

import utils.ImageUtils;

public class VideoDetailsPanel extends JPanel {
	
	private JLabel thumbnailContainer;
	private JTable detailsTable;
	private DefaultTableModel tableModel;
	

    public VideoDetailsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        thumbnailContainer = new JLabel();
        thumbnailContainer.setPreferredSize(new Dimension(200, 120));
        
        add(thumbnailContainer, BorderLayout.WEST);

        String[] columns = {"Propiedad", "Valor"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; 
            }
        };

        detailsTable = new JTable(tableModel);

        detailsTable.setRowHeight(22);

        detailsTable.setShowGrid(false);
        detailsTable.getTableHeader().setVisible(false); 

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    
    public void updateVideoDetails(Youtube video) {
        if (video == null) {
            return;
        }

        try {
        	URI uri = new URI(video.getThumbnailUrl());
        	URL thumbUrl = uri.toURL();
        	ImageIcon icon = new ImageIcon(thumbUrl);
            ImageUtils.resizeImageIcon(icon, 200, 120);
        	thumbnailContainer.setText(null);

        } catch (Exception e) {
        	thumbnailContainer.setIcon(null);
        	thumbnailContainer.setText("No Image");
        }
    }
}
