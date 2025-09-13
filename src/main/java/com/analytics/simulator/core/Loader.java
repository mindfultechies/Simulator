package com.analytics.simulator.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Loader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> loadTemplate(String path) throws IOException {
        String content = Files.readString(Path.of(path));
        return objectMapper.readValue(content, Map.class);
    }
}