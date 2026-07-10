package com.vmi.parkink_manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class FullZoneDto {
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int capacity;
    @Getter
    @Setter
    private int cost;
    @Getter
    @Setter
    private String imageUrl;
}
