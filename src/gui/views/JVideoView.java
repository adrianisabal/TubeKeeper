package gui.views;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JVideoView extends View {
	
	public JVideoView() {
		super(ViewType.SUB_VIEW, null);
		
		JPanel videoContainer = new JPanel();
		videoContainer.setLayout(new BorderLayout());
		
		videoContainer.add(new JTextField("VIDEO GOES HERE"), BorderLayout.CENTER);
		
		JLabel titleContainer = new JLabel("Video Title");
		titleContainer.setAlignmentY(TOP_ALIGNMENT);
		titleContainer.setBorder(BorderFactory.createEmptyBorder(5,10,0,0));
		videoContainer.add(titleContainer, BorderLayout.SOUTH);
		
		add(videoContainer);
	}
}
