class BaseSimulator:
    def __init__(self, template: dict):
        self.template = template

    def generate_event(self) -> dict:
        raise NotImplementedError

    def shutdown(self):
        pass
