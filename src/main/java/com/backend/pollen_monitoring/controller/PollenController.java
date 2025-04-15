package com.backend.pollen_monitoring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.backend.pollen_monitoring.service.PollenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api/pollen")
public class PollenController {

    private final PollenService pollenService;
    private final ObjectMapper objectMapper;

    public PollenController(PollenService pollenService) {
        this.pollenService = pollenService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public RedirectView getPollenDataPage() {
        // Rediriger vers la page HTML statique
        return new RedirectView("/pollen-table.html");
    }
    
    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<JsonNode> getPollenRawData() {
        try {
            String pollenData = pollenService.fetchPollenData();
            // Convertir la chaîne JSON en objet JsonNode
            JsonNode jsonNode = objectMapper.readTree(pollenData);
            return ResponseEntity.ok(jsonNode);
        } catch (JsonProcessingException e) {
            // En cas d'erreur de parsing JSON
            return ResponseEntity.internalServerError().build();
        }
    }
}
