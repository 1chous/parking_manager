package com.vmi.parkink_manager.service;

import com.vmi.parkink_manager.dto.ParkingSessionDto;
import com.vmi.parkink_manager.dto.SessionCreateDto;
import com.vmi.parkink_manager.dto.SessionUpdateDto;
import com.vmi.parkink_manager.exception.NotFoundException;
import com.vmi.parkink_manager.model.ParkZone;
import com.vmi.parkink_manager.model.ParkingSession;
import com.vmi.parkink_manager.repository.SessionRepository;
import com.vmi.parkink_manager.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final ZoneRepository zoneRepository;

    public SessionService(SessionRepository sessionRepository, ZoneRepository zoneRepository) {
        this.sessionRepository = sessionRepository;
        this.zoneRepository = zoneRepository;
    }

    public ParkingSession newEntry(SessionCreateDto dto){
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
        sessionRepository.save(session);
        return session;
    }

    public ParkingSession exit(String vehiclePlate)
    {
        List<ParkingSession> activeSessions = sessionRepository.findActiveByVehiclePlate(vehiclePlate);
        if (activeSessions.isEmpty()){
            throw new NotFoundException("Vehicle not found");
        }
        ParkingSession session = activeSessions.get(0);
        session.setExitTime(LocalDateTime.now());


        // PaymentBill bill = new PaymentBill();
        // bill.setTotal_cost(totalCost);
        // логика оплаты счета
        // по идее то что сессия оплачена надо сделать в другом методе,
        // но пока наверное норм
        session.setIsPaid(true);

        sessionRepository.save(session);
        // save bill
        return session;
    }

    public ParkingSession getById(UUID id) {
        return sessionRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Session not found"));
    }

    public List<ParkingSessionDto> findByParkingZoneId(UUID zoneId) {
        List<ParkingSession> sessions = sessionRepository.findByParkingZoneId(zoneId);

        return sessions.stream().map(session -> {
            ParkingSessionDto dto = new ParkingSessionDto();
            dto.setId(session.getId());
            dto.setParkingZoneId(session.getParkingZone().getId());
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
