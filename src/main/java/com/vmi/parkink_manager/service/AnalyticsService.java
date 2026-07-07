package com.vmi.parkink_manager.service;

import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {
    private SessionService sessionService;
    private ZoneService zoneService;

    public AnalyticsService(SessionService sessionService, ZoneService zoneService) {
        this.sessionService = sessionService;
        this.zoneService = zoneService;
    }


}
