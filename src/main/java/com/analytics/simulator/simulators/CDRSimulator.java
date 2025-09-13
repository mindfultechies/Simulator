package com.analytics.simulator.simulators;

import com.github.javafaker.Faker;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CDRSimulator extends BaseSimulator {
    private final Faker faker = new Faker();
    private final String[] types = {"voice", "sms", "data"};

    public CDRSimulator(Map<String, Object> template) {
        super(template);
    }

    @Override
    public Map<String, Object> generateEvent() {
        Map<String, Object> event = new HashMap<>();
        event.put("callId", UUID.randomUUID().toString());
        event.put("fromNumber", faker.phoneNumber().phoneNumber());
        event.put("toNumber", faker.phoneNumber().phoneNumber());
        event.put("startTime", Instant.now().toString());
        event.put("duration", ThreadLocalRandom.current().nextInt(10, 301)); // 10-300
        event.put("type", types[ThreadLocalRandom.current().nextInt(types.length)]);
        return event;
    }
}