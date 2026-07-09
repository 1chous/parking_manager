package com.vmi.parkink_manager.repository;

import com.vmi.parkink_manager.model.ParkingSession;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<ParkingSession, UUID> {
    @Override
    Optional<ParkingSession> findById(@NonNull UUID uuid);

    @Query(value = "SELECT z.id as zoneId, " +
            "z.name as zoneName, " +
            "COUNT(s.id) as totalSessions, " +
            "COALESCE((COUNT(CASE WHEN s.exit_time IS NULL THEN 1 END) * 100.0 / NULLIF(z.capacity, 0)), 0) as averageOccupancyPercentage, " +
            "COALESCE(AVG(EXTRACT(EPOCH FROM (s.exit_time - s.entry_time)) / 60.0), 0) as averageDurationMinutes, " +
            "COALESCE(SUM(CASE WHEN s.is_paid = true THEN (CEIL(EXTRACT(EPOCH FROM (s.exit_time - s.entry_time)) / 3600.0) * z.cost) ELSE 0 END), 0) as totalRevenue " +
            "FROM park_zone z " +
            "LEFT JOIN parking_session s ON s.parkingzone_id = z.id " +
            "GROUP BY z.id, z.name, z.capacity",
            nativeQuery = true)
    List<Object[]> getAnalyticsSummaryRaw();

    @Override
    void deleteById(@NonNull UUID uuid);

    @Query("SELECT COUNT(s) FROM ParkingSession s WHERE s.parkingZone.id = :zoneId AND s.exitTime IS NULL")
    int countActiveSessionsByZoneId(@Param("zoneId") UUID zoneId);

    @Query("SELECT s FROM ParkingSession s WHERE s.vehiclePlate = :plate AND s.exitTime IS NULL")
    List<ParkingSession> findActiveByVehiclePlate(@Param("plate") String vehiclePlate);

    List<ParkingSession> findByParkingZoneId(UUID parkingZoneId);
}