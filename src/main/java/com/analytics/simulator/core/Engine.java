package com.analytics.simulator.core;

import com.analytics.simulator.simulators.BaseSimulator;
import com.analytics.simulator.simulators.Registry;
import org.apache.kafka.clients.producer.KafkaProducer;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Engine {
    private final Map<String, Object> template;
    private final KafkaProducer<String, String> producer;
    private final double rate;
    private final String topic;
    private final int parallel;
    private final long durationSeconds;
    private final int batchSize;

    public Engine(String templatePath) throws Exception {
        this.template = Loader.loadTemplate(templatePath);
        this.producer = KafkaIO.getProducer();
        Map<String, Object> rateMap = (Map<String, Object>) this.template.get("rate");
        this.rate = ((Number) rateMap.get("per_second")).doubleValue();
        Map<String, Object> outputMap = (Map<String, Object>) this.template.get("output");
        this.topic = (String) outputMap.get("topic");
        this.parallel = template.containsKey("parallel") ? ((Number) template.get("parallel")).intValue() : 1;
        this.durationSeconds = template.containsKey("duration") ? ((Number) template.get("duration")).longValue() : 0;
        this.batchSize = template.containsKey("batch_size") ? ((Number) template.get("batch_size")).intValue() : 1;
    }

    public void run() throws InterruptedException {
        System.out.println("Running simulator " + template.get("id") + " (" + template.get("type") + ") with " + parallel + " parallel threads, batch size " + batchSize + " for " + durationSeconds + " seconds");
        AtomicBoolean stop = new AtomicBoolean(false);

        // Start timer to stop after duration
        if (durationSeconds > 0) {
            Thread timer = new Thread(() -> {
                try {
                    Thread.sleep(1 * 1000);
                    stop.set(true);
                    System.out.println("Duration reached, stopping simulator.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            timer.setDaemon(true);
            timer.start();
        }

        try {
            if (parallel == 1) {
                // Single thread
                String simType = (String) this.template.get("type");
                BaseSimulator simulator = Registry.get(simType, this.template);
                Scheduler.rateController(rate, () -> {
                    for (int i = 0; i < batchSize; i++) {
                        Map<String, Object> event = simulator.generateEvent();
                        KafkaIO.sendEvent(producer, topic, event);
                        System.out.println("Produced: " + event);
                    }
                }, stop::get);
            } else {
                // Parallel execution
                ExecutorService executor = Executors.newFixedThreadPool(parallel);
                double threadRate = rate / parallel;
                for (int i = 0; i < parallel; i++) {
                    String simType = (String) this.template.get("type");
                    BaseSimulator simulator = Registry.get(simType, this.template);
                    executor.submit(() -> {
                        try {
                            Scheduler.rateController(threadRate, () -> {
                                for (int j = 0; j < batchSize; j++) {
                                    Map<String, Object> event = simulator.generateEvent();
                                    KafkaIO.sendEvent(producer, topic, event);
                                    System.out.println("Produced: " + event);
                                }
                            }, stop::get);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }
                executor.shutdown();
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            }
        } catch (Exception e) {
            System.out.println("Stopped simulator.");
            throw e;
        } finally {
            producer.close();
        }
    }
}