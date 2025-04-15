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
        // Données fictives en attendant les vraies (InfluxDB, etc.)
        List<PollutionData> data = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 24; i++) {
            data.add(new PollutionData(
                now.minusHours(23 - i),
                Math.random() * 80,
                Math.random() * 50
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
