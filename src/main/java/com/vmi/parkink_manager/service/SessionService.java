package com.vmi.parkink_manager.service;

import com.vmi.parkink_manager.dto.SessionCreateDto;
import com.vmi.parkink_manager.dto.SessionUpdateDto;
import com.vmi.parkink_manager.model.ParkZone;
import com.vmi.parkink_manager.model.ParkingSession;
import com.vmi.parkink_manager.repository.SessionRepository;
import com.vmi.parkink_manager.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        ParkZone zone = zoneRepository.findById(dto.getParkingZone()).orElseThrow(() -> new RuntimeException("Zone not found"));
        int activeSessions = sessionRepository.findByParkingZoneId(zone.getId()).size();
        if (activeSessions >= zone.getCapacity()){
            throw new RuntimeException("Zone is full");
        }
        ParkingSession session = new ParkingSession();
        session.setId(UUID.randomUUID());
        session.setParkingZone(zone);
        session.setVehiclePlate(dto.getVehiclePlate());
        session.setEntryTime(LocalDateTime.now());
        session.setIsPayed(false);
        sessionRepository.save(session);
        return session;
    }

    public ParkingSession exit(String vehiclePlate)
    {
        List<ParkingSession> activeSessions = sessionRepository.findByVehiclePlate(vehiclePlate);
        if (activeSessions.isEmpty()){
            throw new RuntimeException("Vehicle not found");
        }
        ParkingSession session = activeSessions.get(0);
        session.setExitTime(LocalDateTime.now());
        long minutes = Duration.between(session.getEntryTime(), session.getExitTime()).toMinutes();
        if (minutes == 0){
            minutes = 1;
        }
        if (minutes < 0){
            throw new RuntimeException("Unrealistic exit/entry time");
        }
        long hours = (long) Math.ceil((double) minutes / 60.0);
        double totalCost = hours * session.getParkingZone().getCost();

        // PaymentBill bill = new PaymentBill();
        // bill.setTotal_cost(totalCost);
        // логика оплаты счета
        // по идее то что сессия оплачена надо сделать в другом методе,
        // но пока наверное норм
        session.setIsPayed(true);

        sessionRepository.save(session);
        // save bill
        return session;
    }

    public ParkingSession getById(UUID id) {
        return sessionRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Некорректные параметры или неверный zone_id."));
    }

    public List<ParkingSession> findByParkingZoneId(UUID zoneId) {
        return sessionRepository.findByParkingZoneId(zoneId);
    }

    public ParkingSession update(UUID id, SessionUpdateDto dto) {
        ParkingSession parkingSession = sessionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Парковочная сессия не найдена.")
        );

        if (dto.getIsPaid() && !parkingSession.getIsPayed()) {
            parkingSession.setExitTime(dto.getExitTime());
        }
        parkingSession.setIsPayed(dto.getIsPaid());
        if (dto.getVehiclePlate() != null) {
            parkingSession.setVehiclePlate(dto.getVehiclePlate());
        }
        return sessionRepository.save(parkingSession);
    }

    public void delete(UUID id) {
        sessionRepository.deleteById(id);
    }

}
