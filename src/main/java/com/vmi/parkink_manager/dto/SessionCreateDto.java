package com.vmi.parkink_manager.dto;

import java.util.UUID;

public class SessionCreateDto {
    private UUID parkingZoneId;
    private String vehiclePlate;

    public UUID getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(UUID parkingZone) {
        this.parkingZoneId = parkingZone;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }
}
