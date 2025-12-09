package gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class DownloadsPanel extends JPanel {

	private JPanel container;
    private JButton closeBtn;
    private List<Thread> downloadThreads = new ArrayList<>();


    public DownloadsPanel() {
        setLayout(new BorderLayout());

    	setBorder(BorderFactory.createCompoundBorder(
    			BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel titleLabel = new JLabel("Last Downloads");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        add(titleLabel, BorderLayout.NORTH);
        
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(300, 600)); 


        JScrollPane scroll = new JScrollPane(container);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll, BorderLayout.CENTER);
        scroll.setBorder(null); 

       
        closeBtn = new JButton("Close");

        closeBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                container.removeAll(); 
                downloadThreads.clear();
                
                container.revalidate();
                container.repaint();
                
                setVisible(false);
                closeBtn.setEnabled(false);

            });
        });		

        add(closeBtn, BorderLayout.SOUTH);
    }

    public void addDownload(DownloadItemPanel item) {
        SwingUtilities.invokeLater(() -> {
            container.add(Box.createVerticalStrut(10));
            container.add(item);
            
            container.revalidate();
            container.repaint();
        });
    }

	public JButton getCloseBtn() {
		return closeBtn;
	}

	public List<Thread> getDownloadThreads() {
		return downloadThreads;
	}
   
}
