package com.backend.pollen_monitoring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.backend.pollen_monitoring.service.PollenService;

@Controller
@RequestMapping("/api/pollen")
public class PollenController {

    private final PollenService pollenService;

    public PollenController(PollenService pollenService) {
        this.pollenService = pollenService;
    }

    @GetMapping
    public RedirectView getPollenDataPage() {
        // Rediriger vers la page HTML statique
        return new RedirectView("/pollen-table.html");
    }
    
    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<String> getPollenRawData() {
        String pollenData = pollenService.fetchPollenData();
        return ResponseEntity.ok(pollenData);
    }
    
    @GetMapping("/graph")
    public RedirectView getPollenGraphPage() {
        // Rediriger vers la page de graphiques
        return new RedirectView("/pollen-graph.html");
    }
}
