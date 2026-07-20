CREATE TABLE park_zone (
    id uuid primary key default gen_random_uuid(),
    name text not null,
    capacity int not null,
    cost int not null,
    image_url text
);
CREATE TABLE parking_session (
    id uuid primary key default gen_random_uuid(),
    parkingzone_id uuid not null REFERENCES park_zone(id),
    vehicle_plate text not null,
    entry_time timestamp not null,
    exit_time timestamp,
    is_paid bool not null
);

