package gui.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.views.View.ViewType;

public class SettingsView extends View{

	public SettingsView() {
		super(ViewType.MAIN_VIEW, "Settings");
		
		
		JPanel gridBag = new JPanel(new GridBagLayout());
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5,5,5,5);
		
		//TITLE
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;   // Cuántas columnas de ancho ocupa
		gbc.weightx = 1.0;   // TODO Pilla todo lo que te quede horizontalmente //TODO probar a poner esto en 0
		gbc.fill = GridBagConstraints.HORIZONTAL; // Se estira horizontalmente
		gbc.anchor = GridBagConstraints.CENTER;   // Centrado en su celda
		
		JLabel title = new JLabel("Prueba");
		add(title, gbc);
		
		
//		add(Panel, BorderLayout.CENTER);
	}
	
	

}
