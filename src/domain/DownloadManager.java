package domain;

import java.io.File;
import java.util.function.BiConsumer;

import javax.swing.SwingUtilities;

import com.github.felipeucelli.javatube.Youtube;
import com.github.felipeucelli.javatube.Stream;

import db.VideoDAO;
import db.PlaylistDAO;
import gui.tools.DownloadItemPanel;
import gui.tools.DownloadsPanel;
import io.ConfigManager;
import utils.LinkType;
import utils.TubeUtils;

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
    		
    		LinkType type = LinkType.getLinkType(url);
    		downloadRec(url, type, itemPanel, null, 0);
        });
        
        downloadsPanel.getDownloadThreads().add(t);
        t.start();
    }
    
    
    private void downloadRec(String url, LinkType type, DownloadItemPanel itemPanel, Playlist playlist, int videoIndx) {
        	try {
                switch (type) {
                    case VIDEO:

                      Youtube ytMeta = new Youtube(url);
                      String vTitleMeta = ytMeta.getTitle();
                      itemPanel.setTitle(truncateTitle(vTitleMeta), vTitleMeta);

                      Video video = downloadVideo(url, null, itemPanel);
                      itemPanel.setProgress(100);
                      break;
                    case PLAYLIST:
                        
                    	playlist = downloadPlaylist(url);
                        String pTitle = playlist.getTitle();
                        itemPanel.setTitle(truncateTitle(pTitle), pTitle);
                        
                    	downloadRec(playlist.getVideoUrls().get(0), LinkType.PLAYLIST_VIDEO, itemPanel, playlist, videoIndx);
                    	playlist.setThumbnail();
                        break;
                        
                    case PLAYLIST_VIDEO:
                        if (playlist == null) throw new IllegalArgumentException("Playlist cannot be null");
    
                              video = downloadVideo(url, playlist, itemPanel);
                              videoIndx++;
                              itemPanel.setProgress((int)(((videoIndx) / (double) playlist.getVideoUrls().size()) * 100));
    
                              if (videoIndx < playlist.getVideoUrls().size())
                                  downloadRec(playlist.getVideoUrls().get(videoIndx), LinkType.PLAYLIST_VIDEO, itemPanel, playlist, videoIndx);
                        break;                                            	
                    default:
                    	tryUnknownLink(url, itemPanel);
                }

            } catch (Exception e) {
              e.printStackTrace();
            	String errorTitle = String.format("Error: %s", e.getMessage());
                itemPanel.setTitle(truncateTitle(errorTitle), errorTitle);
            	itemPanel.getTitleLabel().setToolTipText(e.getMessage());
            	
            } finally {
            	checkIfAllFinished();
            }
    }
    
    private void checkIfAllFinished() {
        SwingUtilities.invokeLater(() -> {
            for (Thread t : downloadsPanel.getDownloadThreads()) {

                if (t.isAlive()) {
                    return;
                }
            }
            downloadsPanel.getCloseBtn().setEnabled(true);
        });
    }
    
  private Video downloadVideo(String url, Playlist playlist, DownloadItemPanel itemPanel) {
    try {
        Youtube yt = new Youtube(url);

        if (playlist == null) {
            ConfigManager cfg = new ConfigManager();
            String downloadPath = cfg.getDownloadPath() + File.separator;

            BiConsumer<Long, Long> progressCallback = (bytes, total) -> {
                if (total > 0) {
                    int percent = (int)((bytes * 100) / total);
                    itemPanel.setProgress(percent);
                }
            };

            TubeUtils.downloadVideo(url, downloadsPanel, downloadPath, progressCallback);
        }

        Stream bestStream = yt.streams().getHighestResolution();
        Video video = new Video(yt, bestStream);

        ConfigManager cfg2 = new ConfigManager();
        if (cfg2.isSaveHistory()) {
            if (playlist != null) {
                playlist.getVideos().add(video);
                videoDAO.insert(video, playlist.getDbId()); 
            } else {
                videoDAO.insert(video, null);
            }
        }
        return video;

    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to download video: " + url, e); 		
    } 
}   

  private Playlist downloadPlaylist(String url) {
    	try {
        TubeUtils.downloadPlaylist(url, downloadsPanel);
    	  com.github.felipeucelli.javatube.Playlist apiPlaylist = new com.github.felipeucelli.javatube.Playlist(url);
        Playlist playlist = new Playlist(apiPlaylist);
        ConfigManager cfg = new ConfigManager();
        if (cfg.isSaveHistory()) {
          int playlistId = playlistDAO.insert(playlist);
          playlist.setDbId(playlistId);
        }
        return playlist;
        
        } catch (Exception e) {
            throw new RuntimeException("Failed to download playlist: " + url, e); 		
        } 
    }
    
    private void tryUnknownLink(String url, DownloadItemPanel itemPanel) {
        try {
            Youtube ytTry = new Youtube(url);
            
            if (ytTry.getUrl() != null) {
                downloadRec(url, LinkType.VIDEO, itemPanel, null, 0);
            } else {
                throw new IllegalArgumentException(truncateTitle(String.format("Invalid URL: %s", url)));
            }
            
        } catch (Exception e) {
            throw new IllegalArgumentException(truncateTitle(String.format("Invalid URL: %s", url)), e);
        }
    }
    
    private String truncateTitle(String title) {
        if (title.length() <= MAX_TITLE_LENGTH) return title;
        return title.substring(0, MAX_TITLE_LENGTH - 3) + "...";
    }
}
