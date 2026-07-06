package com.vmi.parkink_manager.service;

import com.vmi.parkink_manager.dto.ZoneCreateDto;
import com.vmi.parkink_manager.model.ParkingSession;
import com.vmi.parkink_manager.repository.SessionRepository;
import com.vmi.parkink_manager.repository.ZoneRepository;
import com.vmi.parkink_manager.model.ParkZone;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final SessionRepository sessionRepository;
    private final String uploadDir = "uploads/";


    public ZoneService(ZoneRepository zoneRepository, SessionRepository sessionRepository) {
        this.zoneRepository = zoneRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<ParkZone> getAll(){
        return zoneRepository.findAll();
    }

    public ParkZone getById(UUID id){
        return zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Zone not founded"));
    }

    public ParkZone zoneCreate(ZoneCreateDto dto){
        ParkZone parkZone = new ParkZone();
        parkZone.setId(UUID.randomUUID());
        parkZone.setName(dto.getName());
        parkZone.setCapacity(dto.getCapacity());
        parkZone.setCost(dto.getCost());
        parkZone.setImageUrl("/api/v1/zones/" + parkZone.getId() + "/image");

        zoneRepository.save(parkZone);
        return parkZone;
    }

    public ParkZone zoneUpdate(UUID id, ZoneCreateDto dto){
        ParkZone parkZone = getById(id);

        parkZone.setName(dto.getName());
        parkZone.setCost(dto.getCost());
        parkZone.setCapacity(dto.getCapacity());
        parkZone.setImageUrl(dto.getImageUrl());

        zoneRepository.save(parkZone);
        return parkZone;
    }

    @Transactional
    public void deleteZone(UUID id){
        List<ParkingSession> parkingSessions = sessionRepository.findByParkingZoneId(id);
        sessionRepository.deleteAll(parkingSessions);
        zoneRepository.deleteById(id);

        File file = new File(uploadDir + id + ".jpg");
        if (file.exists()){
            file.delete();
        }
    }

    public void saveImage(UUID id, byte[] image) throws IOException{
        ParkZone parkZone = getById(id);

        File dir = new File(uploadDir);
        if (!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir, id + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(image);
        }

        parkZone.setImageUrl("/api/v1/zones/" + id + "/image");
        zoneRepository.save(parkZone);
    }

    public byte[] getImage(UUID id) throws IOException{
        File file = new File(uploadDir + id + ".jpg");
        if (!file.exists()){
            throw new RuntimeException("Image not found");
        }
        return Files.readAllBytes(file.toPath());
    }
}
