package main;

import javax.swing.SwingUtilities;

import com.github.felipeucelli.javatube.Youtube;

import gui.MainFrame;
 

public class Main {
    public static void main(String[] args) {
        try {
            // Prueba básica de inicialización
            Youtube yt = new Youtube("https://youtu.be/dQw4w9WgXcQ?si=5RA0rSRyQVzT130u");
            // Lo comento para que no me raye cada vez que ejecuto
//            System.out.println(yt.getTitle());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
                
        SwingUtilities.invokeLater(() -> new MainFrame());
        
    }
}
