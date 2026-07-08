package com.vmi.parkink_manager.controller;

import com.vmi.parkink_manager.dto.AnalyticsDto;
import com.vmi.parkink_manager.service.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<List<AnalyticsDto>> getSummary(){
        return ResponseEntity.ok(analyticsService.createSummary());
    }

}
