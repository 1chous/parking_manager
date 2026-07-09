package com.vmi.parkink_manager.dto;

import com.vmi.parkink_manager.model.ParkingSession;
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

    public static ParkingSessionDto fromEntity(ParkingSession session) {
        ParkingSessionDto dto = new ParkingSessionDto();
        dto.setId(session.getId());
        dto.setParkingZoneId(session.getParkingZone().getId());
        dto.setVehiclePlate(session.getVehiclePlate());
        dto.setEntryTime(session.getEntryTime());
        dto.setExitTime(session.getExitTime());
        dto.setIsPayed(session.getIsPaid());
        return dto;
    }
}