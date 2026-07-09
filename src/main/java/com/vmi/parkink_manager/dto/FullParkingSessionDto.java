package com.vmi.parkink_manager.dto;

import com.vmi.parkink_manager.model.ParkZone;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

public class FullParkingSessionDto {
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private FullZoneDto parkingZone;
    @Getter
    @Setter
    private String vehiclePlate;
    @Getter
    @Setter
    private LocalDateTime entryTime;
    @Getter
    @Setter
    private LocalDateTime exitTime;
    @Getter
    @Setter
    private Boolean isPaid = false;

}
