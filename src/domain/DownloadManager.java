package domain;

import javax.swing.SwingUtilities;

import com.github.felipeucelli.javatube.Youtube;
import com.github.felipeucelli.javatube.Stream;

import db.VideoDAO;
import db.PlaylistDAO;
import gui.tools.DownloadItemPanel;
import gui.tools.DownloadsPanel;
import utils.LinkType;

public class DownloadManager {
	
	private static final int MAX_TITLE_LENGTH = 35; 
	
    private DownloadsPanel downloadsPanel;
    private VideoDAO videoDAO = new VideoDAO();
    private PlaylistDAO playlistDAO = new PlaylistDAO();


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

                if (type == LinkType.UNKNOWN) {
                    try {
                        Youtube ytProbe = new Youtube(url);
                        if (ytProbe.getUrl() != null) {
                            type = LinkType.VIDEO;
                        }
                    } catch (Exception ignored) {
                    }
                }

                switch (type) {
                    case VIDEO:
                        Youtube yt = new Youtube(url);
                        Stream bestStream = yt.streams().getHighestResolution();
                        Video video = new Video(yt, bestStream);
                        
                        itemPanel.setTitle(truncateTitle(video.getTitle()));

                        for (int i = 0; i <= 100; i += 10) {
                            itemPanel.setProgress(i);
                            Thread.sleep(100);
                        }

                        videoDAO.insert(video, null);
                        break;

                    case PLAYLIST:
                        com.github.felipeucelli.javatube.Playlist apiPlaylist =
                                new com.github.felipeucelli.javatube.Playlist(url);
                        Playlist playlist = new Playlist(apiPlaylist);
                        int playlistId = playlistDAO.insert(playlist);
                        playlist.setDbId(playlistId);

                        int total = playlist.getVideos().size();
                        int count = 0;

                        itemPanel.setTitle(truncateTitle(playlist.getTitle()));

                        for (Video v : playlist.getVideos()) {
                            videoDAO.insert(v, playlistId);
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
