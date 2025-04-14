package com.backend.pollen_monitoring.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class PollenService {

    @Value("${pollen.latitude}")
    private double latitude;

    @Value("${pollen.longitude}")
    private double longitude;

    @Value("${openmeteo.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchPollenData() {
        String url = String.format("%s?latitude=%f&longitude=%f&hourly=pm10,pm2_5", apiUrl, latitude, longitude);
        
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erreur API Open-Meteo : " + response.getStatusCode());
        }
    }

    public Map<String, Double> fetchPollenIndices() {
    String url = String.format(
        "%s?latitude=%f&longitude=%f&hourly=pm10,pm2_5,allergens_tree_pollen,allergens_weed_pollen,allergens_grass_pollen",
        apiUrl, latitude, longitude
    );

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
        throw new RuntimeException("Erreur lors de l'appel à Open-Meteo: " + response.getStatusCode());
    }

    try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode hourly = root.path("hourly");

        Map<String, Double> result = new HashMap<>();
        List<String> fields = Arrays.asList("pm10", "pm2_5", "allergens_tree_pollen", "allergens_weed_pollen", "allergens_grass_pollen");

        for (String field : fields) {
            JsonNode array = hourly.path(field);
            if (array.isArray() && array.size() > 0) {
                double latest = array.get(0).asDouble(); // prend la première valeur disponible
                result.put(field, latest);
            }
        }

        return result;
    } catch (Exception e) {
        throw new RuntimeException("Erreur de parsing JSON Open-Meteo", e);
    }   
    }

}
