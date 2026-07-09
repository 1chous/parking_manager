package com.vmi.parkink_manager.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ParkingSessionDto {
    private UUID id;
    private UUID parkingZoneId;
    private String vehiclePlate;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Boolean isPayed;
}