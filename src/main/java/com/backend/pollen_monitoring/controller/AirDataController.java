package com.backend.pollen_monitoring.controller;

import com.backend.pollen_monitoring.models.PollutionData;
import com.backend.pollen_monitoring.service.PollenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AirDataController {

    @Autowired
    private PollenService pollenService;

    @GetMapping("/pollution")
    public List<PollutionData> getPollutionData() {
        // Récupérer les données brutes de pollen
        String pollenDataRaw = pollenService.fetchPollenData();
        
        try {
            // Créer une liste pour stocker les données
            List<PollutionData> data = new ArrayList<>();
            
            // Utiliser le service pour extraire les données horaires
            Map<LocalDateTime, Map<String, Double>> hourlyData = pollenService.extractHourlyPollenData(pollenDataRaw);
            
            // Convertir les données en objets PollutionData
            for (Map.Entry<LocalDateTime, Map<String, Double>> entry : hourlyData.entrySet()) {
                LocalDateTime timestamp = entry.getKey();
                Map<String, Double> pollenValues = entry.getValue();
                
                // Créer un objet PollutionData avec uniquement les données de pollen (sans PM10/PM25 fictifs)
                data.add(new PollutionData(timestamp, 0.0, 0.0, pollenValues));
            }
            
            // Trier les données par timestamp
            data.sort(Comparator.comparing(PollutionData::getTimestamp));
            
            return data;
        } catch (Exception e) {
            // En cas d'erreur, retourner une liste vide
            return Collections.emptyList();
        }
    }

    @GetMapping("/pollens")
    public Map<String, Double> getPollenData() {
        // Récupérer les données de pollen en appelant le service Pollen
        return pollenService.fetchPollenIndices();
    }
}
