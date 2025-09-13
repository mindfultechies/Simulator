package com.analytics.simulator.simulators;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Registry {
    private static final Map<String, Function<Map<String, Object>, BaseSimulator>> simulators = new HashMap<>();

    static {
        simulators.put("cdr", CDRSimulator::new);
        simulators.put("device", DeviceSimulator::new);
        simulators.put("sse", SSESimulator::new);
    }

    public static BaseSimulator get(String type, Map<String, Object> template) {
        Function<Map<String, Object>, BaseSimulator> constructor = simulators.get(type);
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown simulator type: " + type);
        }
        return constructor.apply(template);
    }
}