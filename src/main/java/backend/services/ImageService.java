package backend.services;

import backend.models.ImageRecord;
import backend.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final ImageRepository repository;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    public byte[] processAndSave(MultipartFile file) throws IOException {
        // 1. Ler imagem
        BufferedImage originalInfo = ImageIO.read(file.getInputStream());
        
        // 2. Inverter (Usando sua lógica XOR) 
        BufferedImage invertedInfo = invertColors(originalInfo);

        // 3. Converter para bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(invertedInfo, "png", baos);
        byte[] invertedBytes = baos.toByteArray();

        // 4. Salvar no Banco
        ImageRecord record = new ImageRecord();
        record.setFileName(file.getOriginalFilename());
        record.setProcessedAt(LocalDateTime.now());
        record.setOriginalImage(file.getBytes());
        record.setInvertedImage(invertedBytes);
        
        repository.save(record);

        return invertedBytes;
    }

    private BufferedImage invertColors(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgba = image.getRGB(x, y);
                // Sua lógica XOR 
                int col = (rgba & 0x00FFFFFF) ^ 0xFFFFFF; 
                result.setRGB(x, y, col);
            }
        }
        return result;
    }
}