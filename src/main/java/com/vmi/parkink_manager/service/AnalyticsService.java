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

//    public List<AnalyticsDto> createSummary() {
//        List<ParkZone> allZones = zoneService.getAll();
//        List<ParkingSession> allSessions = sessionService.getAll();
//        List<AnalyticsDto> answer = new ArrayList<>();
//
//        for (ParkZone zone: allZones) {
//            double averageDurationMinutes = 0.0;
//            double averageOccupancyPercentage = 0.0;
//            double totalRevenue = 0.0;
//
//            List<ParkingSession> required = new ArrayList<>();
//            long totalDurationMinutes = 0;
//            for (ParkingSession session: allSessions) {
//                // session.getParkingZoneId() != null
//                // вроде бы у нас уже стоит в orm, что это поле не null
//                if (session.getParkingZoneId().getId().equals(zone.getId())) {
//                    required.add(session);
//                    if (session.getIsPayed()) {
//                        long delta = Duration.between(session.getEntryTime(), session.getExitTime()).toMinutes();
//                        totalDurationMinutes += delta;
//                        totalRevenue += calcTotalCost(session, delta);
//                    }
//                }
//            }
//            int totalSessions = required.size();
//            averageDurationMinutes = totalDurationMinutes / (double)totalSessions;
//            averageOccupancyPercentage = totalSessions / (double)zone.getCapacity() * 100;
//
//            AnalyticsDto response = new AnalyticsDto();
//            response.setZoneId(zone.getId());
//            response.setZoneName(zone.getName());
//            response.setTotalSessions(totalSessions);
//            response.setAverageOccupancyPercentage(averageOccupancyPercentage);
//            response.setAverageDurationMinutes(averageDurationMinutes);
//            response.setTotalRevenue(totalRevenue);
//
//            answer.add(response);
//        }
//        return answer;
//    }

//    private double calcTotalCost(ParkingSession session, long deltaInMinutes) {
//        // long deltaInMinutes = Duration.between(session.getEntryTime(), session.getExitTime()).toMinutes();
//        if (deltaInMinutes == 0){
//            deltaInMinutes = 1;
//        }
//        if (deltaInMinutes < 0){
//            throw new RuntimeException("Unrealistic exit/entry time");
//        }
//        long hours = (long) Math.ceil((double) deltaInMinutes / 60.0);
//        return hours * session.getParkingZoneId().getCost();
//    }

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
