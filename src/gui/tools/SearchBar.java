package gui.tools;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchBar extends JPanel {
	private JLabel icon;
	private JTextField txtContainer;
	
	public SearchBar() {
		
		JLabel icon = new JLabel();		
		this.icon = icon;
		add(icon);
		
		JTextField txtContainer = new JTextField();
		this.txtContainer = txtContainer;
		add(txtContainer);
		
		txtContainer.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		txtContainer.setAlignmentY(CENTER_ALIGNMENT);
		txtContainer.setBackground(null);
		
		setBorder(BorderFactory.createLineBorder(new Color(45,45,48)));
		
	}
	
	public void setIcon(String path) {
		this.icon.setIcon(new ImageIcon(path));
	}
	
	public void setText(String txt) {
		this.txtContainer.setText(txt);
	}
}
