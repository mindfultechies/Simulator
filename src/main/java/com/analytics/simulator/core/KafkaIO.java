package com.analytics.simulator.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class KafkaIO {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static KafkaProducer<String, String> getProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    public static void sendEvent(KafkaProducer<String, String> producer, String topic, Object event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);
            producer.send(record);
        } catch (Exception e) {
            System.err.println("Error sending event: " + e.getMessage());
        }
    }
}