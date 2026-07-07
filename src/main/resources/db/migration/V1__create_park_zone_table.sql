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
    is_payed bool not null
);

/* шаблон для второй таблицы
CREATE TABLE todo_items (
id UUID PRIMARY KEY,
category_id UUID NOT NULL,
title VARCHAR(255) NOT NULL,
description TEXT,
priority VARCHAR(50) NOT NULL,
deadline_at TIMESTAMP,
is_completed BOOLEAN NOT NULL,
created_at TIMESTAMP,
completed_at TIMESTAMP,
CONSTRAINT fk_todo_category FOREIGN KEY (category_id) REFERENCES task_categories(id)
);*/