package com.analytics.simulator.core;

import java.util.function.BooleanSupplier;

public class Scheduler {
    public static void rateController(double eventsPerSecond, Runnable action, BooleanSupplier stopCondition) throws InterruptedException {
        long intervalMillis = (long) (1000.0 / 1);
        while (!stopCondition.getAsBoolean()) {
            action.run();
            Thread.sleep(intervalMillis);
        }
    }

    public static void rateController(double eventsPerSecond, Runnable action) throws InterruptedException {
        rateController(eventsPerSecond, action, () -> false);
    }
}