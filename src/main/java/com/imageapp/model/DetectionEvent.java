package com.imageapp.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidade que representa um evento de detecção de IA
 * Armazenado no Sentinel Database (PostgreSQL)
 */
@Entity
@Table(name = "detection_events", indexes = {
    @Index(name = "idx_camera_timestamp", columnList = "camera_id,timestamp"),
    @Index(name = "idx_detected_class", columnList = "detected_class"),
    @Index(name = "idx_storage_ref", columnList = "storage_ref_key")
})
public class DetectionEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "camera_id", nullable = false, length = 100)
    private String cameraId;
    
    @Column(nullable = false)
    private Instant timestamp;
    
    @Column(name = "detected_class", nullable = false, length = 50)
    private String detectedClass;
    
    @Column(nullable = false, precision = 5, scale = 4)
    private Double confidence;
    
    @Column(name = "storage_ref_key", nullable = false)
    private String storageRefKey;
    
    @Column(name = "encrypted_metadata", columnDefinition = "TEXT")
    private String encryptedMetadata;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
    
    // Construtores
    public DetectionEvent() {}
    
    public DetectionEvent(String cameraId, String detectedClass, Double confidence, 
                         String storageRefKey) {
        this.cameraId = cameraId;
        this.detectedClass = detectedClass;
        this.confidence = confidence;
        this.storageRefKey = storageRefKey;
    }
    
    // Getters e Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getCameraId() {
        return cameraId;
    }
    
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getDetectedClass() {
        return detectedClass;
    }
    
    public void setDetectedClass(String detectedClass) {
        this.detectedClass = detectedClass;
    }
    
    public Double getConfidence() {
        return confidence;
    }
    
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
    
    public String getStorageRefKey() {
        return storageRefKey;
    }
    
    public void setStorageRefKey(String storageRefKey) {
        this.storageRefKey = storageRefKey;
    }
    
    public String getEncryptedMetadata() {
        return encryptedMetadata;
    }
    
    public void setEncryptedMetadata(String encryptedMetadata) {
        this.encryptedMetadata = encryptedMetadata;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
