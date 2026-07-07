package com.vmi.parkink_manager.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionCreateDto {
    private UUID parkingZoneId;
    private String vehiclePlate;

    public UUID getParkingZone() {
        return parkingZoneId;
    }

    public void setParkingZone(UUID parkingZone) {
        this.parkingZoneId = parkingZone;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }
}
