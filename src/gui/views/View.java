package gui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JPanel {
	
	public View() {
	
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1000, 700));
		
	}
	
	public void setTitle(String title) {
		JLabel titleContainer = new JLabel();
		titleContainer.setText(title);
		
		titleContainer.setFont(new Font(null,1,28));
		titleContainer.setBorder(BorderFactory.createEmptyBorder(10,20,0,0));
		
		add(titleContainer, BorderLayout.NORTH);
	}

	
	public void setBackButton() {
		JButton back = new JButton();
		back.setIcon(new ImageIcon("resources/images/searchIcon.png"));
		
		add(back, BorderLayout.NORTH);

	}
}

