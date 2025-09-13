from kafka import KafkaProducer
import json

def get_producer():
    return KafkaProducer(
        bootstrap_servers="localhost:9092",
        value_serializer=lambda v: json.dumps(v).encode("utf-8")
    )

def send_event(producer, topic, event):
    producer.send(topic, value=event)
