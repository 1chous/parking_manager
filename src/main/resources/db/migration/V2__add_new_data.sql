INSERT INTO park_zone (name, capacity, cost, image_url)
VALUES ('Парковка 1', 100, 10, 'image/first_parking.jpg'),
       ('Парковка 2', 50, 20, 'image/second_parking.jpg');


INSERT INTO parking_session (parkingzone_id, vehicle_plate, entry_time, exit_time, is_payed)
VALUES ((SELECT id FROM park_zone WHERE name = 'Парковка 1'),
        'A123AA777',
        '2021-01-01 09:00:00',
        '2021-01-01 10:00:00',
        true),
       ((SELECT id FROM park_zone WHERE name = 'Парковка 2'),
        'B777BB77',
        '2021-01-01 10:00:00',
        '2021-01-01 13:00:00',
        false);
