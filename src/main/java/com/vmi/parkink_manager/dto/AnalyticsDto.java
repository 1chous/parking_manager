package com.vmi.parkink_manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class AnalyticsDto {
    @Getter
    @Setter
    private UUID zoneId;
    @Getter
    @Setter
    private String zoneName;
    @Getter
    @Setter
    private int totalSessions;
    @Getter
    @Setter
    private double averageOccupancyPercentage;
    @Getter
    @Setter
    private double averageDurationMinutes;
    @Getter
    @Setter
    private double totalRevenue;
}
