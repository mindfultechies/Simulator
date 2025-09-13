package com.analytics.simulator.simulators;

import com.github.javafaker.Faker;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class DeviceSimulator extends BaseSimulator {
    private final Faker faker = new Faker();
    private final Random random = new Random();

    public DeviceSimulator(Map<String, Object> template) {
        super(template);
    }

    @Override
    public Map<String, Object> generateEvent() {
        Map<String, Object> event = new HashMap<>();
        event.put("deviceId", UUID.randomUUID().toString());
        event.put("timestamp", Instant.now().toString());
        event.put("battery", Math.round(ThreadLocalRandom.current().nextDouble(0, 100) * 100.0) / 100.0);

        Map<String, Object> gps = new HashMap<>();
        gps.put("lat", Math.round(ThreadLocalRandom.current().nextDouble(12.8, 13.2) * 1000000.0) / 1000000.0);
        gps.put("lon", Math.round(ThreadLocalRandom.current().nextDouble(80.0, 80.3) * 1000000.0) / 1000000.0);
        event.put("gps", gps);

        double temperature = 36.5 + random.nextGaussian() * 0.8;
        event.put("temperature", Math.round(temperature * 100.0) / 100.0);

        return event;
    }
}