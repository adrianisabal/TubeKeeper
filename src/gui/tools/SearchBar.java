package gui.tools;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchBar extends JPanel {
	
	public SearchBar(String txt) {
		
		JLabel iconContainer = new JLabel();
		iconContainer.setIcon(new ImageIcon("resources/images/searchIcon.png"));
		add(iconContainer);
		
		JTextField txtContainer = new JTextField(txt);
		add(txtContainer);
		
		
		txtContainer.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		txtContainer.setAlignmentY(CENTER_ALIGNMENT);
		txtContainer.setBackground(null);
		
		setBorder(BorderFactory.createLineBorder(new Color(45,45,48)));
		
	}
}
	
