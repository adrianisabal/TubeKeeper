package main;

import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

import gui.MainFrame;
import domain.Video;
import domain.Playlist;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Video> SAMPLE_VIDEOS = new ArrayList<>();
    public static ArrayList<Playlist> SAMPLE_PLAYLISTS = new ArrayList<>();

    public static void main(String[] args) {
        generateSampleVideos();
        generateSamplePlaylists();

        SwingUtilities.invokeLater(() -> new MainFrame());
    }
    // AI GENERATED SAMPLE DATA
    private static void generateSampleVideos() {
        String[] authors = {
                "Fireship",
                "The Coding Train",
                "Joma Tech",
                "Computerphile",
                "freeCodeCamp.org",
                "Traversy Media",
                "sentdex",
                "Corey Schafer",
                "Two Minute Papers",
                "Kurzgesagt – In a Nutshell"
        };

        String[] topics = {
                "Java", "Spring Boot", "Git", "Clean Code", "Refactoring",
                "Microservices", "Testing", "Hexagonal Architecture",
                "Design Patterns", "Kotlin", "Docker", "Kubernetes"
        };

        String[] formats = {
                "Tutorial", "Crash Course", "Live Coding", "Walkthrough",
                "Explained", "Talk", "Best Practices", "Quick Guide"
        };

        ImageIcon thumb = new ImageIcon("resources/images/logo.png");

        for (int i = 0; i < 40; i++) {
            String author = authors[i % authors.length];
            String topic = topics[i % topics.length];
            String format = formats[i % formats.length];
            String title = format + " on " + topic + " #" + ((i % 7) + 1);

            Video v = new Video(title, author, thumb);
            SAMPLE_VIDEOS.add(v);
        }
    }

    private static void generateSamplePlaylists() {
        String[] authors = {
                "Local User",
                "Backend Team",
                "Frontend Squad",
                "DevOps Crew",
                "Java Enthusiasts",
                "Kotlin Fans",
                "Refactoring Ninjas",
                "Clean Coders",
                "Random Mix",
                "Daily Picks"
        };

        String[] playlistTypes = {
                "Best of",
                "Fundamentals of",
                "Quick Guides to",
                "Advanced collection of",
                "Introduction to",
                "Recommended practices for",
                "Deep dive into",
                "Daily playlist of",
                "Intensive summary of",
                "Random collection of"
        };

        ImageIcon thumb = new ImageIcon("resources/images/logo.png");

        for (int i = 0; i < 10; i++) {
            String author = authors[i % authors.length];
            String type = playlistTypes[i % playlistTypes.length];
            String title = type + " programming " + (2020 + (i % 5));

            Playlist p = new Playlist(title, author, thumb);

            int startIndex = (i * 3) % SAMPLE_VIDEOS.size();
            int numVideos = 4 + (i % 3);

            for (int j = 0; j < numVideos; j++) {
                int idx = (startIndex + j) % SAMPLE_VIDEOS.size();
                Video v = SAMPLE_VIDEOS.get(idx);
                p.getVideos().add(v);
                p.getVideoUrls().add("https://www.youtube.com/watch?v=VIDEO_" + (idx + 1));
            }

            SAMPLE_PLAYLISTS.add(p);
        }
    }
}
