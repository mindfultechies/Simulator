package com.analytics.simulator.simulators;

import java.util.Map;

public abstract class BaseSimulator {
    protected final Map<String, Object> template;

    public BaseSimulator(Map<String, Object> template) {
        this.template = template;
    }

    public abstract Map<String, Object> generateEvent();

    public void shutdown() {
        // Default implementation - no-op
    }
}