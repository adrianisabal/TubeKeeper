package gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.ImageUtils;

public class SearchBar extends JPanel {
	private String placeholder;
	
	public SearchBar(String txt) {
		
		this.placeholder = txt;
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300,50));
		
		JLabel iconContainer = new JLabel();
		
		iconContainer.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("resources/images/searchIcon.png"),18,18));
		add(iconContainer, BorderLayout.WEST);
		
		JTextField txtContainer = new JTextField(txt);
		add(txtContainer, BorderLayout.CENTER);
		
		//iconContainer.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		txtContainer.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		txtContainer.setAlignmentY(CENTER_ALIGNMENT);
		txtContainer.setBackground(null);
		
		//setBorder(BorderFactory.createLineBorder(new Color(45,45,48)));
		setBorder(BorderFactory.createCompoundBorder(
				new RoundedBorder(15),
				BorderFactory.createEmptyBorder(0,0,0,0)
				));
		
		//setBorder(new RoundedBorder(15));
		
		txtContainer.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtContainer.setText("");
			}
		});
		
		
		txtContainer.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				txtContainer.setText(placeholder);
			}
		});
			
	}
}
	
