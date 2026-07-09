package com.vmi.parkink_manager.controller;

import com.vmi.parkink_manager.dto.ParkingSessionDto;
import com.vmi.parkink_manager.dto.ZoneCreateDto;
import com.vmi.parkink_manager.model.ParkZone;
import com.vmi.parkink_manager.model.ParkingSession;
import com.vmi.parkink_manager.service.SessionService;
import com.vmi.parkink_manager.service.ZoneService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/zones")
public class ZoneController {
    private final ZoneService zoneService;
    private final SessionService sessionService;

    public ZoneController(ZoneService zoneService, SessionService sessionService) {
        this.zoneService = zoneService;
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<ParkZone> create(@RequestBody ZoneCreateDto dto) {
        return new ResponseEntity<>(zoneService.zoneCreate(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ParkZone>> getAll() {
        return ResponseEntity.ok(zoneService.getAll());
    }

    @GetMapping("/{zone_id}")
    public ResponseEntity<ParkZone> getById(@PathVariable("zone_id") UUID id) {
        return ResponseEntity.ok(zoneService.getById(id));
    }

    @PutMapping("/{zone_id}")
    public ResponseEntity<ParkZone> update(@PathVariable("zone_id") UUID id, @RequestBody ZoneCreateDto dto) {
        return ResponseEntity.ok(zoneService.zoneUpdate(id, dto));
    }

    @DeleteMapping("/{zone_id}")
    public ResponseEntity<Void> delete(@PathVariable("zone_id") UUID id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{zone_id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(@PathVariable("zone_id") UUID id, @RequestParam("file") MultipartFile file) throws IOException {
        zoneService.saveImage(id, file.getBytes());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{zone_id}/image")
    public void getImage(@PathVariable("zone_id") UUID id, HttpServletResponse response) throws IOException {
        byte[] image = zoneService.getImage(id);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.getOutputStream().write(image);
        response.getOutputStream().flush();
    }

    @GetMapping("/{zone_id}/sessions")
    public ResponseEntity<List<ParkingSessionDto>> findParkingSessions(@PathVariable("zone_id") UUID id) {
        return ResponseEntity.ok(sessionService.findByParkingZoneId(id));
    }
}