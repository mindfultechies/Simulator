import uuid, random
from datetime import datetime
from faker import Faker
from simulators.base import BaseSimulator

faker = Faker()

class CDRSimulator(BaseSimulator):
    def generate_event(self):
        return {
            "callId": str(uuid.uuid4()),
            "fromNumber": faker.msisdn(),
            "toNumber": faker.msisdn(),
            "startTime": datetime.utcnow().isoformat(),
            "duration": random.randint(10, 300),
            "type": random.choice(["voice", "sms", "data"])
        }
    