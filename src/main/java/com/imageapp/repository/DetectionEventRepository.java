package com.imageapp.repository;

import com.imageapp.model.DetectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Repository para eventos de detecção
 */
@Repository
public interface DetectionEventRepository extends JpaRepository<DetectionEvent, UUID> {
    
    /**
     * Busca eventos por câmera ordenados por timestamp
     */
    List<DetectionEvent> findByCameraIdOrderByTimestampDesc(String cameraId);
    
    /**
     * Busca eventos por classe detectada
     */
    List<DetectionEvent> findByDetectedClass(String detectedClass);
    
    /**
     * Busca eventos em um intervalo de tempo
     */
    List<DetectionEvent> findByTimestampBetween(Instant start, Instant end);
    
    /**
     * Busca eventos de uma câmera em um período
     */
    @Query("SELECT e FROM DetectionEvent e WHERE e.cameraId = :cameraId " +
           "AND e.timestamp BETWEEN :start AND :end " +
           "ORDER BY e.timestamp DESC")
    List<DetectionEvent> findByCameraAndTimeRange(
        @Param("cameraId") String cameraId,
        @Param("start") Instant start,
        @Param("end") Instant end
    );
    
    /**
     * Conta eventos por classe
     */
    @Query("SELECT e.detectedClass, COUNT(e) FROM DetectionEvent e " +
           "GROUP BY e.detectedClass")
    List<Object[]> countByDetectedClass();
    
    /**
     * Busca eventos com confiança acima de um threshold
     */
    @Query("SELECT e FROM DetectionEvent e WHERE e.confidence >= :threshold " +
           "ORDER BY e.timestamp DESC")
    List<DetectionEvent> findHighConfidenceEvents(@Param("threshold") Double threshold);
}
