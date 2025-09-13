import json
from pathlib import Path

def load_template(path: str) -> dict:
    with open(Path(path), "r") as f:
        return json.load(f)
