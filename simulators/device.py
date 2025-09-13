import uuid, random
from datetime import datetime
from faker import Faker
from simulators.base import BaseSimulator

faker = Faker()

class DeviceSimulator(BaseSimulator):
    def generate_event(self):
        return {
            "deviceId": str(uuid.uuid4()),
            "timestamp": datetime.utcnow().isoformat(),
            "battery": round(random.uniform(0, 100), 2),
            "gps": {
                "lat": round(random.uniform(12.8, 13.2), 6),
                "lon": round(random.uniform(80.0, 80.3), 6)
            },
            "temperature": round(random.normalvariate(36.5, 0.8), 2)
        }
