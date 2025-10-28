package main;

import com.github.felipeucelli.javatube.Youtube;

public class Main {
    public static void main(String[] args) {
        try {
            // Prueba básica de inicialización
            Youtube yt = new Youtube("https://youtu.be/dQw4w9WgXcQ?si=5RA0rSRyQVzT130u");
            System.out.println(yt.getTitle());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
