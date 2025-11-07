package gui.tools;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

import domain.Video;
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

    public void updateVideoDetails(Video video) {
    if (video == null) {
        return;
    }

    try {
        ImageIcon resized = ImageUtils.resizeImageIcon(video.getThumbnail(), 200, 120);
        thumbnailContainer.setIcon(resized);
        thumbnailContainer.setText(null);
    } catch (Exception e) {
        thumbnailContainer.setIcon(null);
        thumbnailContainer.setText("No Image");
    }

    tableModel.setRowCount(0);
    tableModel.addRow(new Object[]{"Title", video.getTitle()});
    tableModel.addRow(new Object[]{"Author", video.getAuthor()});
    tableModel.addRow(new Object[]{"Size", (int) (Math.random() * 512)}); // si existe
}

}
