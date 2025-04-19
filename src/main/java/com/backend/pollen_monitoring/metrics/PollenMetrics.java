package com.backend.pollen_monitoring.metrics;

import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.pollen_monitoring.service.PollenService;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class PollenMetrics {

    private final PollenService pollenService;
    private final MeterRegistry registry;

    public PollenMetrics(PollenService pollenService, MeterRegistry registry) {
        this.pollenService = pollenService;
        this.registry = registry;
        
        // Enregistrer les métriques dès le démarrage
        registerPollenMetrics();
    }

    @Scheduled(fixedRate = 3600000) // toutes les heures
    public void updateMetrics() {
        registerPollenMetrics();
    }
    
    private void registerPollenMetrics() {
        try {
            Map<String, Double> indices = pollenService.fetchPollenIndices();
            indices.forEach((type, value) -> {
                // Utiliser des gauges avec des tags pour faciliter le filtrage dans Grafana
                Gauge.builder("pollen.index", () -> value)
                     .tag("type", type)
                     .description("Indice de pollen pour " + type)
                     .register(registry);
            });
        } catch (Exception e) {
            System.err.println("Erreur de récupération des données pollen: " + e.getMessage());
        }
    }
}
