package Entities;

import ImageInverterGUI;

public static BufferedImage implements ImageNode(){
     private BufferedImage processImage(File file){
            try {
                Image img = ImageIO.read(file);
                    BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight());
                    Graphics g = bi.getGraphics();
                for (int i = 0; i < img.getHeight(); i++) {
                    for (int j = 0; j < img.getWidth(); j++) {
                        int[] pixels = ImageProcessing.getPixels(img, j, i);
                        g.setColor(new Color(pixels[0], pixels[1], pixels[2]));
                        g.fillRect(j, i, 1, 1);
                    }
                }
                g.dispose();
                displayImage(bi);
            } catch (IOException e) {
                status.setText("Error reading file: " + e.getMessage());
            }
        }

        private void displayImage(BufferedImage bi) {
            // Update the preview panels
            // This would involve setting the image on the UI components.
        }

        public static void main(String[] args) {
            new BufferedImage();
        }

        public throwException(DefaultImageNode dis){
            return new exception.ioException(dis);
}       }
    
};
