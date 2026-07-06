package com.vmi.parkink_manager.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionCreateDto {
    private UUID parkingZone;
    private String vehiclePlate;

    public UUID getParkingZone() {
        return parkingZone;
    }

    public void setParkingZone(UUID parkingZone) {
        this.parkingZone = parkingZone;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }
}
