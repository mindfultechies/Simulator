import time
from core.loader import load_template
from core.scheduler import rate_controller
from core.kafka_io import get_producer, send_event
from simulators.registry import registry

class Engine:
    def __init__(self, template_path):
        self.template = load_template(template_path)
        print(self.template)
        sim_type = self.template["type"]
        print(sim_type)

        self.simulator = registry.get(sim_type)(self.template)
        self.producer = get_producer()
        self.rate = self.template["rate"]["per_second"]
        self.topic = self.template["output"]["topic"]

    def run(self):
        print(f"Running simulator {self.template['id']} ({self.template['type']})")
        try:
            for _ in rate_controller(self.rate):
                event = self.simulator.generate_event()
                send_event(self.producer, self.topic, event)
                print("Produced:", event)
        except KeyboardInterrupt:
            self.simulator.shutdown()
            print("Stopped simulator.")

if __name__ == "__main__":
   import sys
   if len(sys.argv) != 2:
       print("Usage: python -m core.engine <template_path>")
       sys.exit(1)
   engine = Engine(sys.argv[1])
   engine.run()
