package Entities;


import java.io.File;
import javax.swing.SwingWorker;

public static class ImageProcessingWorker extends SwingWorker<Object, Object> {

    private BufferedImage invImage;
    private File file;

    public ImageProcessingWorker(File file) {
        this.file = file;
    }

    @Override
    protected void doInBackground() throws Exception {
        invImage = processImage(file);
    }

    public BufferedImage getInvImage() {
        return invImage;
    }
}
