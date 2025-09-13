import random
from datetime import datetime
from simulators.base import BaseSimulator

class SSESimulator(BaseSimulator):
    def generate_event(self):
        return {
            "event": "status_update",
            "payload": {
                "status": random.choice(["ok", "warn", "fail"]),
                "ts": datetime.utcnow().isoformat()
            }
        }
