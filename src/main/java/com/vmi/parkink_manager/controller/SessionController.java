package com.vmi.parkink_manager.controller;

import com.vmi.parkink_manager.dto.ParkingSessionDto;
import com.vmi.parkink_manager.dto.SessionCreateDto;
import com.vmi.parkink_manager.dto.SessionUpdateDto;
import com.vmi.parkink_manager.model.ParkingSession;
import com.vmi.parkink_manager.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<ParkingSessionDto> create(
            @RequestBody SessionCreateDto dto
    ) {
        ParkingSession session = sessionService.newEntry(dto);
        ParkingSessionDto responseDto = ParkingSessionDto.fromEntity(session);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{session_id}")
    public ResponseEntity<ParkingSessionDto> getById(
            @PathVariable("session_id") UUID id
            ){
        ParkingSession session = sessionService.getById(id);
        return ResponseEntity.ok(ParkingSessionDto.fromEntity(session));
    }

    @PutMapping("/{session_id}")
    public ResponseEntity<ParkingSessionDto> update(
            @PathVariable("session_id") UUID id,
            @RequestBody SessionUpdateDto dto
            ) {
        ParkingSession session = sessionService.update(id, dto);
        return ResponseEntity.ok(ParkingSessionDto.fromEntity(session));
    }

    @DeleteMapping("/{session_id}")
    public ResponseEntity<Void> delete(
            @PathVariable("session_id") UUID id
    ) {
        sessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
