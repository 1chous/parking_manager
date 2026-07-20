package com.vmi.parkink_manager.service;

import com.vmi.parkink_manager.dto.AnalyticsDto;
import com.vmi.parkink_manager.model.ParkZone;
import com.vmi.parkink_manager.model.ParkingSession;
import com.vmi.parkink_manager.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnalyticsService {
    private final SessionService sessionService;
    private final ZoneService zoneService;

    private final SessionRepository sessionRepository;

    public AnalyticsService(SessionRepository sessionRepository, SessionService sessionService, ZoneService zoneService) {
        this.sessionService = sessionService;
        this.zoneService = zoneService;
        this.sessionRepository = sessionRepository;
    }



    @Transactional(readOnly = true)
    public List<AnalyticsDto> createSummary() {
        List<Object[]> data = sessionRepository.getAnalyticsSummaryRaw();
        List<AnalyticsDto> result = new ArrayList<>();

        for (Object[] row : data) {
            AnalyticsDto dto = new AnalyticsDto();
            dto.setZoneId((UUID) row[0]);
            dto.setZoneName((String) row[1]);
            dto.setTotalSessions(((Long) row[2]).intValue());
            dto.setAverageOccupancyPercentage(row[3] != null ? ((Number) row[3]).doubleValue() : 0.0);
            dto.setAverageDurationMinutes(row[4] != null ? ((Number) row[4]).doubleValue() : 0.0);
            dto.setTotalRevenue(row[5] != null ? ((Number) row[5]).doubleValue() : 0.0);
            result.add(dto);
        }
        return result;
    }
}
