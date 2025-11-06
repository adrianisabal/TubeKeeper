package gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchBar extends JPanel {
	private String placeholder;
	
	public SearchBar(String txt) {
		
		this.placeholder = txt;
		
		setLayout(new BorderLayout());
		
		JLabel iconContainer = new JLabel();
		iconContainer.setIcon(new ImageIcon("resources/images/searchIcon.png"));
		add(iconContainer, BorderLayout.WEST);
		
		JTextField txtContainer = new JTextField(txt);
		add(txtContainer, BorderLayout.CENTER);
		
		iconContainer.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		txtContainer.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		txtContainer.setAlignmentY(CENTER_ALIGNMENT);
		txtContainer.setBackground(null);
		
		setBorder(BorderFactory.createLineBorder(new Color(45,45,48)));
		
		
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
	
