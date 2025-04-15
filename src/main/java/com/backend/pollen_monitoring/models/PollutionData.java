package com.backend.pollen_monitoring.models;

import java.time.LocalDateTime;
import java.util.Map;

public class PollutionData {
    private LocalDateTime timestamp;
    private double pm10;
    private double pm25;
    private Map<String, Double> pollenIndices;  // Ajout des indices de pollen

    // Constructeur principal avec indices de pollen
    public PollutionData(LocalDateTime timestamp, double pm10, double pm25, Map<String, Double> pollenIndices) {
        this.timestamp = timestamp;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.pollenIndices = pollenIndices;
    }

    // Constructeur sans les indices de pollen (pour les anciens cas)
    public PollutionData(LocalDateTime timestamp, double pm10, double pm25) {
        this.timestamp = timestamp;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.pollenIndices = null;  // Ou initialise avec une valeur par défaut si nécessaire
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getPm10() {
        return pm10;
    }

    public double getPm25() {
        return pm25;
    }

    public Map<String, Double> getPollenIndices() {
        return pollenIndices;
    }
}
