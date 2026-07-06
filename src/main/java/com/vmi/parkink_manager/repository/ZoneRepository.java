package com.vmi.parkink_manager.repository;

import com.vmi.parkink_manager.model.ParkZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZoneRepository extends JpaRepository<ParkZone, UUID> {
    ParkZone findByName(String name);
}
