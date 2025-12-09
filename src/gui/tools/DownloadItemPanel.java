package gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class DownloadItemPanel extends JPanel {
	
	private JProgressBar progressBar;
    private JLabel titleLabel;

    public DownloadItemPanel(String title) {
    	setPreferredSize(new Dimension(250, 80));
    	setMinimumSize(new Dimension(250, 80));
    	setMaximumSize(new Dimension(250, 80));

    	setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.GRAY, 1), 
                   BorderFactory.createEmptyBorder(5, 10, 5, 10) 
           ));
    	    	
    	titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        add(titleLabel, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
    }

    public void setProgress(int value) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(value));
    }

    public void setTitle(String title) {
        SwingUtilities.invokeLater(() -> titleLabel.setText(title));
    }
    
    public JLabel getTitleLabel() {
        return titleLabel;
    }

    


}


