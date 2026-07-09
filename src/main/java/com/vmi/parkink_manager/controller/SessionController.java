package com.vmi.parkink_manager.controller;

import com.vmi.parkink_manager.dto.FullParkingSessionDto;
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
    public ResponseEntity<FullParkingSessionDto> create(
            @RequestBody SessionCreateDto dto
    ) {
        return new ResponseEntity<>(sessionService.newEntry(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{session_id}")
    public ResponseEntity<FullParkingSessionDto> getById(
            @PathVariable("session_id") UUID id
    ){
        return ResponseEntity.ok(sessionService.getById(id));
    }

    @PutMapping("/{session_id}")
    public ResponseEntity<ParkingSession> update(
            @PathVariable("session_id") UUID id,
            @RequestBody SessionUpdateDto dto
    ) {
        return ResponseEntity.ok(sessionService.update(id, dto));
    }

    @DeleteMapping("/{session_id}")
    public ResponseEntity<Void> delete(
            @PathVariable("session_id") UUID id
    ) {
        sessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
