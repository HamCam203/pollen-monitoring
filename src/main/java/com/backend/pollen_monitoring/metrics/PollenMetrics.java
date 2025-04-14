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
    }

    @Scheduled(fixedRate = 3600000) // toutes les heures
    public void updateMetrics() {
        try {
            Map<String, Double> indices = pollenService.fetchPollenIndices();
            indices.forEach((type, value) -> 
                Gauge.builder("pollen_" + type + "_index", value::doubleValue)
                     .description("Indice de pollen pour " + type)
                     .register(registry)
            );
        } catch (Exception e) {
            System.err.println("Erreur de récupération des données pollen: " + e.getMessage());
        }
    }
}

