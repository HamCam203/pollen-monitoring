package com.backend.pollen_monitoring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.pollen_monitoring.service.PollenService;

@RestController
@RequestMapping("/api/pollen")
public class PollenController {

    private final PollenService pollenService;

    public PollenController(PollenService pollenService) {
        this.pollenService = pollenService;
    }

    @GetMapping
    public ResponseEntity<String> getPollenData() {
        String pollenData = pollenService.fetchPollenData();
        return ResponseEntity.ok(pollenData);
    }
}
