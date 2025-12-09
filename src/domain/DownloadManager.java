package domain;

import javax.swing.SwingUtilities;

import com.github.felipeucelli.javatube.Youtube;

import db.VideoDAO;
import gui.tools.DownloadItemPanel;
import gui.tools.DownloadsPanel;
import utils.LinkType;

public class DownloadManager {
	
	private static final int MAX_TITLE_LENGTH = 35; 
	
    private DownloadsPanel downloadsPanel;
    private VideoDAO videoDAO = new VideoDAO();


    public DownloadManager(DownloadsPanel downloadsPanel) {
        this.downloadsPanel = downloadsPanel;
    }

    public void download(String url) {
    	downloadsPanel.setVisible(true);
        downloadsPanel.getCloseBtn().setEnabled(false);
        
        DownloadItemPanel itemPanel = new DownloadItemPanel("Downloading...");
        downloadsPanel.addDownload(itemPanel);

        Thread t = new Thread(() -> {
            try {
                LinkType type = LinkType.getLinkType(url);

                switch (type) {
                    case VIDEO:
                        Youtube yt = new Youtube(url);
                        Video video = new Video(yt);
                        
                        itemPanel.setTitle(truncateTitle(video.getTitle()));

                        for (int i = 0; i <= 100; i += 10) {
                            itemPanel.setProgress(i);
                            Thread.sleep(100);
                        }

                        videoDAO.insert(video, null);
                        break;

                    case PLAYLIST:
                        Playlist playlist = new Playlist(new com.github.felipeucelli.javatube.Playlist(url));
                        int total = playlist.getVideos().size();
                        int count = 0;

                        itemPanel.setTitle(truncateTitle(playlist.getTitle()));

                        for (Video v : playlist.getVideos()) {
                        	
                            videoDAO.insert(v, null);
                            count++;
                            
                            int progress = (int)((count / (double) total) * 100);
                            itemPanel.setProgress(progress);
                            Thread.sleep(100);
                        }
                        break;

                    default:
                    	throw new IllegalArgumentException(truncateTitle(String.format("Invalid URL: %s", url)));
                }

            } catch (Exception e) {
            	itemPanel.setTitle(truncateTitle(String.format("Error: %s", e.getMessage())));
            	itemPanel.getTitleLabel().setToolTipText(e.getMessage());
            } finally {
            	checkIfAllFinished();
            }
        });
        
        downloadsPanel.getDownloadThreads().add(t);
        t.start();
    }
    
    private void checkIfAllFinished() {
        SwingUtilities.invokeLater(() -> {
            for (Thread t : downloadsPanel.getDownloadThreads()) {

                if (t.isAlive()) {
                    downloadsPanel.getCloseBtn().setEnabled(false);
                    return;
                }
            }
            downloadsPanel.getCloseBtn().setEnabled(true);
        });
    }
    

    private String truncateTitle(String title) {
        if (title.length() <= MAX_TITLE_LENGTH) return title;
        return title.substring(0, MAX_TITLE_LENGTH - 3) + "...";
    }

}
