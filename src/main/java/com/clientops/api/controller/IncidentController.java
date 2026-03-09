package com.clientops.api.controller;

import com.clientops.api.dto.IncidentResponse;
import com.clientops.service.IncidentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/incidents")
public class IncidentController {
    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getIncidents(
        @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(incidentService.getIncidents(status));
    }

    @PostMapping("/{id}/resolve")
    public ResponseEntity<Void> resolve(@PathVariable Long id) {
        incidentService.resolveIncident(id);
        return ResponseEntity.ok().build();
    }
}
