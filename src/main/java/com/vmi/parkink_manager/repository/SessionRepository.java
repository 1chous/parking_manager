package com.vmi.parkink_manager.repository;

import com.vmi.parkink_manager.model.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<ParkingSession, UUID> {
    List<ParkingSession> findByParkingZoneId(UUID parkingZoneId);
}