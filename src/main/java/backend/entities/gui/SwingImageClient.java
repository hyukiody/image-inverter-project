package backend.entities.gui;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class SwingImageClient {

    private final String API_URL = "http://localhost:8080/api/images/invert";

    public void sendImage(File imageFile) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            
            // Construção manual de Multipart Body (simplificado)
            // Em produção, use bibliotecas como Apache HttpComponents para Multipart
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/octet-stream") // Simplificação
                .POST(HttpRequest.BodyPublishers.ofFile(imageFile.toPath()))
                .build();

            // Envio Assíncrono para não travar a GUI (Event Dispatch Thread)
            client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        byte[] invertedBytes = response.body();
                        updateSwingUI(invertedBytes); // Atualiza JLabel com a nova imagem
                    }
                });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateSwingUI(byte[] imageData) {
        SwingUtilities.invokeLater(() -> {
            ImageIcon icon = new ImageIcon(imageData);
            // labelDisplay.setIcon(icon);
            // labelDisplay.repaint();
        });
    }
}