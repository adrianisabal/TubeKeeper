package gui.tools;

import java.awt.BorderLayout;
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
	private JTextField txtContainer;
    private boolean clearOnFocusLost; 
    
	public SearchBar(String txt, boolean clearOnFocusLost) {
		
		this.placeholder = txt;
        this.clearOnFocusLost = clearOnFocusLost;
        
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300,50));
		
		JLabel iconContainer = new JLabel();
		
		iconContainer.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("resources/images/searchIcon.png"),18,18));
		add(iconContainer, BorderLayout.WEST);
		
		txtContainer = new JTextField(txt);
		add(txtContainer, BorderLayout.CENTER);
		
		txtContainer.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		txtContainer.setAlignmentY(CENTER_ALIGNMENT);
		txtContainer.setBackground(null);
		
		setBorder(BorderFactory.createCompoundBorder(
				new RoundedBorder(15),
				BorderFactory.createEmptyBorder(0,0,0,0)
				));
		
		
		txtContainer.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				  if (txtContainer.getText().equals(placeholder)) {
	                    txtContainer.setText("");
	                }			
		   }

			@Override
			public void focusLost(FocusEvent e) {
				if (clearOnFocusLost) {
                    txtContainer.setText(placeholder);
				} else if (txtContainer.getText().isBlank()) {
                    txtContainer.setText(placeholder);
                }
            }
		});
	}
	
	public JTextField getTextContainer() {
		return txtContainer;
	}
	
	public void setOnEnterAction(java.util.function.Consumer<String> action) {
		this.txtContainer.addActionListener(e -> {
			String text = this.txtContainer.getText();
			action.accept(text);
		});	
	}	
}	
