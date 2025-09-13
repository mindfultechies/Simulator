package com.analytics.simulator.simulators;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SSESimulator extends BaseSimulator {
    private final String[] statuses = {"ok", "warn", "fail"};

    public SSESimulator(Map<String, Object> template) {
        super(template);
    }

    @Override
    public Map<String, Object> generateEvent() {
        Map<String, Object> event = new HashMap<>();
        event.put("event", "status_update");

        Map<String, Object> payload = new HashMap<>();
        payload.put("status", statuses[ThreadLocalRandom.current().nextInt(statuses.length)]);
        payload.put("ts", Instant.now().toString());
        event.put("payload", payload);

        return event;
    }
}