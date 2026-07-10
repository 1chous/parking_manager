package com.vmi.parkink_manager.service;

import com.vmi.parkink_manager.dto.*;
import com.vmi.parkink_manager.exception.NotFoundException;
import com.vmi.parkink_manager.model.ParkZone;
import com.vmi.parkink_manager.model.ParkingSession;
import com.vmi.parkink_manager.repository.SessionRepository;
import com.vmi.parkink_manager.repository.ZoneRepository;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SessionService {
    private final SessionRepository sessionRepository;
    private final ZoneRepository zoneRepository;

    public SessionService(SessionRepository sessionRepository, ZoneRepository zoneRepository) {
        this.sessionRepository = sessionRepository;
        this.zoneRepository = zoneRepository;
    }

    public FullParkingSessionDto newEntry(SessionCreateDto dto){
        ParkZone zone = zoneRepository.findById(dto.getParkingZoneId()).orElseThrow(() -> new NotFoundException("Zone not found"));
        int activeSessions = sessionRepository.countActiveSessionsByZoneId(zone.getId());
        if (activeSessions >= zone.getCapacity()){
            throw new RuntimeException("Zone is full");
        }
        ParkingSession session = new ParkingSession();
        session.setId(UUID.randomUUID());
        session.setParkingZone(zone);
        session.setVehiclePlate(dto.getVehiclePlate());
        session.setEntryTime(LocalDateTime.now());
        session.setIsPaid(false);
        return toDto(sessionRepository.save(session));
    }

    private FullZoneDto toZoneDto(ParkZone zone) {

        FullZoneDto dto = new FullZoneDto();

        dto.setId(zone.getId());
        dto.setName(zone.getName());
        dto.setCapacity(zone.getCapacity());
        dto.setCost(zone.getCost());
        dto.setImageUrl(zone.getImageUrl());
        return dto;
    }
    private FullParkingSessionDto toDto(ParkingSession session) {
        FullParkingSessionDto dto = new FullParkingSessionDto();
        dto.setId(session.getId());
        dto.setParkingZone(toZoneDto(session.getParkingZone()));
        dto.setVehiclePlate(session.getVehiclePlate());
        dto.setEntryTime(session.getEntryTime());
        dto.setExitTime(session.getExitTime());
        dto.setIsPaid(session.getIsPaid());
        return dto;
    }


    @Transactional(readOnly = true)
    public FullParkingSessionDto getById(UUID id) {
        ParkingSession s =  sessionRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Session not found"));
        return toDto(s);
    }

    @Transactional(readOnly = true)
    public List<ParkingSessionDto> findByParkingZoneId(UUID zoneId) {
        List<ParkingSession> sessions = sessionRepository.findByParkingZoneId(zoneId);

        return sessions.stream().map(session -> {
            ParkingSessionDto dto = new ParkingSessionDto();
            dto.setId(session.getId());
            dto.setParkingZoneId(zoneId);
            dto.setVehiclePlate(session.getVehiclePlate());
            dto.setEntryTime(session.getEntryTime());
            dto.setExitTime(session.getExitTime());
            dto.setIsPayed(session.getIsPaid());
            return dto;
        }).toList();
    }

    public ParkingSession update(UUID id, SessionUpdateDto dto) {
        ParkingSession parkingSession = sessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Парковочная сессия не найдена.")
        );
        if (dto.getExitTime() != null) {
            parkingSession.setExitTime(dto.getExitTime());
        }
        if (dto.getIsPaid() != null) {
            parkingSession.setIsPaid(dto.getIsPaid());
        }
        if (dto.getVehiclePlate() != null) {
            parkingSession.setVehiclePlate(dto.getVehiclePlate());
        }
        return sessionRepository.save(parkingSession);
    }

    public void delete(UUID id) {
        sessionRepository.deleteById(id);
    }

    public List<ParkingSession> getAll() {
        return sessionRepository.findAll();
    }
}
