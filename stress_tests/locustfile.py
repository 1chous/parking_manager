from locust import HttpUser, task

class ParkingUser(HttpUser):

    @task
    def get_zones(self):
        self.client.get("/api/v1/zones")