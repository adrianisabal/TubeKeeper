package domain;

import gui.tools.DownloadItemPanel;

public class DownloadProgress {

    private final DownloadItemPanel itemPanel;

    public DownloadProgress(DownloadItemPanel itemPanel) {
        this.itemPanel = itemPanel;
    }

    public void set(int value) {
        int v = Math.max(0, Math.min(100, value));
        itemPanel.setProgress(v);
    }

    public void advance(int start, int end, long totalMillis) {
        if (end <= start || totalMillis <= 0) {
            set(end);
            return;
        }
        int range = end - start;
        long stepMillis = totalMillis / range;
        for (int i = start; i < end; i++) {
            set(i);
            try {
                Thread.sleep(stepMillis);
            } catch (InterruptedException e) {
                set(end);
                return;
            }
        }
        set(end);
    }
}
