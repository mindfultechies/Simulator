package com.analytics.simulator;

import com.analytics.simulator.core.Engine;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar simulator.jar <template_path>");
            System.exit(1);
        }

        try {
            Engine engine = new Engine(args[0]);
            engine.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}