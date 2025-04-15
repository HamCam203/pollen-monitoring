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
        // Récupérer les vraies données de pollen
        Map<String, Double> pollenIndices = pollenService.fetchPollenIndices();
        
        // Créer des données historiques (24 heures) mais avec les vraies données de pollen
        List<PollutionData> data = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
    
        for (int i = 0; i < 24; i++) {
            data.add(new PollutionData(
                now.minusHours(23 - i),
                Math.random() * 80,  // PM10 toujours simulé
                Math.random() * 50,  // PM25 toujours simulé
                pollenIndices        // Données de pollen réelles
            ));
        }
    
        return data;
    }

    @GetMapping("/pollens")
    public Map<String, Double> getPollenData() {
        // Récupérer les données de pollen en appelant le service Pollen
        return pollenService.fetchPollenIndices();
    }
}
