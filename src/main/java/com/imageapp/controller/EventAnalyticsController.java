package com.imageapp.controller;

import com.imageapp.model.DetectionEvent;
import com.imageapp.service.EventIngestionService;
import com.imageapp.service.EventIngestionService.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event Analytics Controller
 * 
 * Endpoints para ingestão de eventos e analytics
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Configurar CORS adequadamente em produção
public class EventAnalyticsController {
    
    private static final Logger logger = LoggerFactory.getLogger(EventAnalyticsController.class);
    
    @Autowired
    private EventIngestionService eventService;
    
    /**
     * FLUXO AZUL: Endpoint de ingestão de eventos
     */
    @PostMapping("/ingest")
    public ResponseEntity<Map<String, Object>> ingestEvent(@RequestBody EventDTO eventDTO) {
        logger.info("📥 Recebendo evento de detecção");
        
        try {
            DetectionEvent saved = eventService.ingestEvent(eventDTO);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("eventId", saved.getId().toString());
            response.put("timestamp", saved.getCreatedAt().toString());
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            logger.warn("❌ Validação falhou: {}", e.getMessage());
            
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(error);
            
        } catch (Exception e) {
            logger.error("❌ Erro ao processar evento", e);
            
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", "Internal server error");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Busca eventos por câmera
     */
    @GetMapping("/events/camera/{cameraId}")
    public ResponseEntity<List<DetectionEvent>> getEventsByCamera(
            @PathVariable String cameraId
    ) {
        List<DetectionEvent> events = eventService.getEventsByCamera(cameraId);
        return ResponseEntity.ok(events);
    }
    
    /**
     * Busca eventos por classe detectada
     */
    @GetMapping("/events/class/{detectedClass}")
    public ResponseEntity<List<DetectionEvent>> getEventsByClass(
            @PathVariable String detectedClass
    ) {
        List<DetectionEvent> events = eventService.getEventsByClass(detectedClass);
        return ResponseEntity.ok(events);
    }
    
    /**
     * Busca eventos em período
     */
    @GetMapping("/events/range")
    public ResponseEntity<List<DetectionEvent>> getEventsByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end
    ) {
        List<DetectionEvent> events = eventService.getEventsByTimeRange(start, end);
        return ResponseEntity.ok(events);
    }
    
    /**
     * Analytics Summary - Dashboard
     */
    @GetMapping("/analytics/summary")
    public ResponseEntity<Map<String, Object>> getAnalyticsSummary() {
        Map<String, Object> summary = eventService.getAnalyticsSummary();
        return ResponseEntity.ok(summary);
    }
    
    /**
     * Health check do middleware
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "middleware");
        return ResponseEntity.ok(health);
    }
}
