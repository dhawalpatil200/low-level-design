package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerFactory {
    private static final LoggerFactory INSTANCE = new LoggerFactory();
    private final Map<String, Logger> loggers = new ConcurrentHashMap<>();
    private LoggerConfig config;

    public static LoggerFactory getLoggerFactory() {
        return INSTANCE;
    }

    private LoggerFactory() { }

    // initialize once
    public void initialize(LoggerConfig config) {
        this.config = config;
    }

    public Logger getLogger(String name) {
        return loggers.computeIfAbsent(name, this::createLogger);
    }

    private Logger createLogger(String name) {
        return new Logger(name, config.getAppenders(), config.getLevel());
    }
}
