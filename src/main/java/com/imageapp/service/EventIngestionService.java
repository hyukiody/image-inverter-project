package com.imageapp.service;

import com.imageapp.model.DetectionEvent;
import com.imageapp.repository.DetectionEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

/**
 * FLUXO AZUL (Blue Flow): Event Ingestion & Analytics Service
 * 
 * Responsabilidades:
 * - Receber eventos do Edge Node
 * - Validar e sanitizar dados
 * - Persistir no Sentinel Database
 * - Fornecer analytics para o Frontend
 */
@Service
public class EventIngestionService {
    
    private static final Logger logger = LoggerFactory.getLogger(EventIngestionService.class);
    
    @Autowired
    private DetectionEventRepository eventRepository;
    
    @Value("${microkernel.url}")
    private String microkernelUrl;
    
    /**
     * Processa e persiste evento de detecção
     */
    @Transactional
    public DetectionEvent ingestEvent(EventDTO eventDTO) {
        logger.info("🔵 FLUXO AZUL: Processando evento - Camera: {}, Class: {}", 
                   eventDTO.getCameraId(), eventDTO.getDetectedClass());
        
        // Valida dados
        validateEvent(eventDTO);
        
        // Sanitiza dados sensíveis
        Map<String, Object> sanitizedMetadata = sanitizeMetadata(eventDTO.getMetadata());
        
        // Cria entidade
        DetectionEvent event = new DetectionEvent();
        event.setCameraId(eventDTO.getCameraId());
        event.setDetectedClass(eventDTO.getDetectedClass());
        event.setConfidence(eventDTO.getConfidence());
        event.setStorageRefKey(eventDTO.getStorageRefKey());
        event.setTimestamp(parseTimestamp(eventDTO.getTimestamp()));
        
        // Se houver metadados sensíveis, criptografa
        if (sanitizedMetadata != null && !sanitizedMetadata.isEmpty()) {
            String encryptedMeta = encryptSensitiveData(sanitizedMetadata);
            event.setEncryptedMetadata(encryptedMeta);
        }
        
        // Persiste
        DetectionEvent saved = eventRepository.save(event);
        
        logger.info("✓ Evento persistido: ID={}", saved.getId());
        return saved;
    }
    
    /**
     * Valida campos obrigatórios do evento
     */
    private void validateEvent(EventDTO dto) {
        List<String> errors = new ArrayList<>();
        
        if (dto.getCameraId() == null || dto.getCameraId().isBlank()) {
            errors.add("camera_id é obrigatório");
        }
        
        if (dto.getDetectedClass() == null || dto.getDetectedClass().isBlank()) {
            errors.add("detected_class é obrigatório");
        }
        
        if (dto.getConfidence() == null || dto.getConfidence() < 0 || dto.getConfidence() > 1) {
            errors.add("confidence deve estar entre 0.0 e 1.0");
        }
        
        if (dto.getStorageRefKey() == null || dto.getStorageRefKey().isBlank()) {
            errors.add("storage_ref_key é obrigatório");
        }
        
        if (!errors.isEmpty()) {
            String message = "Validação falhou: " + String.join(", ", errors);
            logger.warn("⚠️ {}", message);
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Sanitiza metadados removendo informações sensíveis demais
     */
    private Map<String, Object> sanitizeMetadata(Map<String, Object> metadata) {
        if (metadata == null) return null;
        
        Map<String, Object> sanitized = new HashMap<>(metadata);
        
        // Remove coordenadas GPS exatas
        if (sanitized.containsKey("gps_lat")) {
            sanitized.remove("gps_lat");
        }
        if (sanitized.containsKey("gps_lon")) {
            sanitized.remove("gps_lon");
        }
        
        // Remove IPs
        sanitized.remove("client_ip");
        sanitized.remove("device_ip");
        sanitized.remove("ip_address");
        
        return sanitized;
    }
    
    /**
     * Criptografa dados sensíveis usando o Microkernel
     * (chamada HTTP ao endpoint de attribute encryption)
     */
    private String encryptSensitiveData(Map<String, Object> data) {
        // Por enquanto, apenas serializa como JSON
        // Em produção, chamar Microkernel para attribute-level encryption
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper()
                    .writeValueAsString(data);
        } catch (Exception e) {
            logger.error("Erro ao serializar metadados", e);
            return "{}";
        }
    }
    
    /**
     * Parse timestamp do formato ISO-8601
     */
    private Instant parseTimestamp(String timestamp) {
        if (timestamp == null || timestamp.isBlank()) {
            return Instant.now();
        }
        try {
            return Instant.parse(timestamp);
        } catch (Exception e) {
            logger.warn("Timestamp inválido, usando atual: {}", timestamp);
            return Instant.now();
        }
    }
    
    /**
     * Busca eventos por câmera
     */
    public List<DetectionEvent> getEventsByCamera(String cameraId) {
        return eventRepository.findByCameraIdOrderByTimestampDesc(cameraId);
    }
    
    /**
     * Busca eventos por classe
     */
    public List<DetectionEvent> getEventsByClass(String detectedClass) {
        return eventRepository.findByDetectedClass(detectedClass);
    }
    
    /**
     * Busca eventos em período
     */
    public List<DetectionEvent> getEventsByTimeRange(Instant start, Instant end) {
        return eventRepository.findByTimestampBetween(start, end);
    }
    
    /**
     * Retorna estatísticas agregadas
     */
    public Map<String, Object> getAnalyticsSummary() {
        Map<String, Object> analytics = new HashMap<>();
        
        // Total de eventos
        long totalEvents = eventRepository.count();
        analytics.put("totalEvents", totalEvents);
        
        // Eventos por classe
        List<Object[]> classCounts = eventRepository.countByDetectedClass();
        Map<String, Long> byClass = new HashMap<>();
        for (Object[] row : classCounts) {
            byClass.put((String) row[0], (Long) row[1]);
        }
        analytics.put("eventsByClass", byClass);
        
        // Eventos de alta confiança (>= 0.8)
        List<DetectionEvent> highConf = eventRepository.findHighConfidenceEvents(0.8);
        analytics.put("highConfidenceCount", highConf.size());
        
        return analytics;
    }
    
    /**
     * DTO para receber eventos
     */
    public static class EventDTO {
        private String cameraId;
        private String timestamp;
        private String detectedClass;
        private Double confidence;
        private String storageRefKey;
        private Map<String, Object> metadata;
        
        // Getters e Setters
        public String getCameraId() { return cameraId; }
        public void setCameraId(String cameraId) { this.cameraId = cameraId; }
        
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
        
        public String getDetectedClass() { return detectedClass; }
        public void setDetectedClass(String detectedClass) { this.detectedClass = detectedClass; }
        
        public Double getConfidence() { return confidence; }
        public void setConfidence(Double confidence) { this.confidence = confidence; }
        
        public String getStorageRefKey() { return storageRefKey; }
        public void setStorageRefKey(String storageRefKey) { this.storageRefKey = storageRefKey; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    }
}
