import random
from locust import HttpUser, task, between

class ParkingManagerLoadTester(HttpUser):
    wait_time = between(0.05, 0.2)

    def on_start(self):
        self.zone_ids = []
        
        pixel_1x1_jpeg = (
            b'\xff\xd8\xff\xe0\x00\x10JFIF\x00\x01\x01\x01\x00`\x00`\x00\x00\xff\xdb\x00C\x00\x08\x06\x06'
            b'\x07\x06\x05\x08\x07\x07\x07\t\t\x08\n\x0c\x14\x08\x08\x0b\x0b\x16\x10\x12\x0c\x14\x1d\x1a'
            b'\x1f\x1e\x1d\x1a\x1c\x1c\x20$*.\x20"#(,-.(&#\'\xff\xc0\x00\x0b\x08\x00\x01\x00\x01\x01\x01'
            b'\x11\x00\xff\xc4\x00\x1f\x00\x00\x01\x05\x01\x01\x01\x01\x01\x01\x00\x00\x00\x00\x00\x00'
            b'\x00\x00\x01\x02\x03\x04\x05\x06\x07\x08\t\n\x0b\xff\xda\x00\x08\x01\x01\x00\x00?\x00\x37'
            b'\xaa\x00\x01\xff\xd9'
        )
        
        for i in range(5):
            response = self.client.post("/api/v1/zones", json={
                "name": f"Terminal Area {i}",
                "capacity": 1000000,
                "cost": 150
            })
            
            if response.status_code == 201:
                zone_id = response.json()["id"]
                self.zone_ids.append(zone_id)
                
                self.client.put(
                    f"/api/v1/zones/{zone_id}/image",
                    files={"file": ("map.jpg", pixel_1x1_jpeg, "image/jpeg")}
                )

    @task(40)
    def get_sessions_by_zone(self):
        if self.zone_ids:
            random_zone_id = random.choice(self.zone_ids)
            self.client.get(f"/api/v1/zones/{random_zone_id}/sessions")

    @task(4)
    def create_new_parking_session(self):
        if self.zone_ids:
            random_zone_id = random.choice(self.zone_ids)
            
            letters = "ABEKMHOPCTYX"
            plate = (
                f"{random.choice(letters)}"
                f"{random.randint(100, 999):03d}"
                f"{random.choice(letters)}"
                f"{random.choice(letters)}"
                f"{random.choice(['77', '99', '197', '777'])}"
            )
            
            self.client.post("/api/v1/sessions", json={
                "parkingZoneId": random_zone_id,
                "vehiclePlate": plate
            })

    @task(1)
    def get_analytics_summary(self):
        self.client.get("/api/v1/analytics/summary")