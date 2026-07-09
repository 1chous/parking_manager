package com.vmi.parkink_manager.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessionUpdateDto {
    @Getter
    @Setter
    private LocalDateTime exitTime;
    @Getter
    @Setter
    private String vehiclePlate;
    @Getter
    @Setter
    private Boolean isPaid;
}
