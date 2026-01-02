package com.imageapp.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageProcessingService {

    public BufferedImage readImage(byte[] imageBytes) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(input);
            if (image == null) {
                throw new IOException("Unsupported image format or corrupted data");
            }
            return image;
        }
    }

    public byte[] writeImage(BufferedImage image, String format) throws IOException {
        String outputFormat = (format == null || format.isBlank()) ? "png" : format;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            if (!ImageIO.write(image, outputFormat, output)) {
                throw new IOException("Unable to encode image as " + outputFormat);
            }
            return output.toByteArray();
        }
    }

    public BufferedImage invertImage(BufferedImage image) {
        BufferedImage inverted = new BufferedImage(
            image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgba = image.getRGB(x, y);
                int a = (rgba >> 24) & 0xFF;
                int r = 255 - ((rgba >> 16) & 0xFF);
                int g = 255 - ((rgba >> 8) & 0xFF);
                int b = 255 - (rgba & 0xFF);
                inverted.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return inverted;
    }

    public BufferedImage convertToGrayscale(BufferedImage image) {
        return ImageFiltersService.convertToGrayscale(image);
    }

    public BufferedImage applyBlur(BufferedImage image, int radius) {
        return ImageFiltersService.applyBlur(image, radius);
    }

    public BufferedImage adjustBrightness(BufferedImage image, float factor) {
        return ImageFiltersService.adjustBrightness(image, factor);
    }

    public BufferedImage adjustContrast(BufferedImage image, float factor) {
        return ImageFiltersService.adjustContrast(image, factor);
    }

    public BufferedImage applySharpen(BufferedImage image) {
        return ImageFiltersService.applySharpen(image);
    }
}
