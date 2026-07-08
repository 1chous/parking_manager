CREATE INDEX idx_parking_session_vehicle_active ON parking_session (vehicle_plate) WHERE exit_time IS NULL;

CREATE INDEX idx_parking_session_zone_active ON parking_session (parkingzone_id) WHERE exit_time IS NULL;