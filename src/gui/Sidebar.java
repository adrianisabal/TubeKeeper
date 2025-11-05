package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Sidebar extends JPanel {
	
	private boolean isExpanded = false;
	private List<DefaultButton> menuButtons = new ArrayList<>();
	private DefaultButton expandBut;
	private ImageIcon iconExpand = new ImageIcon("resources/images/menuIcon.png");
	
	
	
	public Sidebar() {
		
		setLayout(new BorderLayout()); //TODO Cambiar esto a Border Layout
		setPreferredSize(new Dimension(70, 750));
		setBackground(new Color(255,255,255)); //45, 45, 48 COLOR OBSCURE
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setMaximumSize(new Dimension(250, 750));
		
		createButtons();
	}
	
	private void toggleSidebar() {
		Image image =  iconExpand.getImage();
		ImageIcon resizedIcon = new ImageIcon(image.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
		isExpanded = !isExpanded;
		
		if (isExpanded) {
			for (DefaultButton but : menuButtons) {
				but.setCollapsed(false);
				setPreferredSize(new Dimension(250, 750));
				expandBut.setNewDimension(new Dimension(210,60));
				expandBut.setIcon(iconExpand); //TODO cambiar por el icono
			}
		} else {
			for (DefaultButton but : menuButtons) {
				but.setCollapsed(true);
				setPreferredSize(new Dimension(70, 750));
				expandBut.setNewDimension(new Dimension(60,60));
				expandBut.setIcon(iconExpand); // TODO Cambiar por el icono
			}
		}
	}
	
	private void createButtons() {
		expandedActionListener();
		String[] names = {"Search", "Playlists Menu", "History"};
		for (int i = 0; i < names.length ; i++) {
			
		DefaultButton newButton = new DefaultButton(names[i]);
		
		if (!isExpanded) {
			newButton.setCollapsed(true);
		}
		
		if (i != names.length) {
			add(Box.createRigidArea(new Dimension(0,30)));
		}
		
		menuButtons.add(newButton);
		add(newButton);
		}
		
		add(Box.createVerticalGlue());
		setSettingsButton();
	}
	

	
	private void expandedActionListener() {
		
		expandBut = new DefaultButton(iconExpand, new Dimension(60,60));
		expandBut.setBorder(null);		
		
		expandBut.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	toggleSidebar();
		    }
		});

		add(Box.createVerticalStrut(10)); //TODO
		add(expandBut, BorderLayout.NORTH); // TODO Meterlo a la izquierda para meter a la derecha el nombre de TubeKeeper
		add(Box.createRigidArea(new Dimension(0,30))); //TODO
		add(new JSeparator()); //TODO
		
	}
	
	private void setSettingsButton() {
		DefaultButton settingsBut = new DefaultButton("Settings");
		
		if (!isExpanded) {
			settingsBut.setCollapsed(true);
		}
		
		menuButtons.add(settingsBut);
		
		add(Box.createVerticalStrut(30)); //TODO
		add(settingsBut, BorderLayout.SOUTH);
		add(Box.createVerticalStrut(10)); //TODO Tienes que añadir un panel dentro de cada border para poder meter más elementos
		
	}

}
