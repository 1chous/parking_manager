package com.vmi.parkink_manager.dto;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessionUpdateDto {
    @Getter
    private LocalDateTime exitTime;
    @Getter
    private String vehiclePlate;
    @Getter
    private Boolean isPaid;
}
