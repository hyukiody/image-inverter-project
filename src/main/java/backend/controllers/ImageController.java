package backend.controllers;

import backend.services.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(value = "/invert", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] result = service.processAndSave(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}