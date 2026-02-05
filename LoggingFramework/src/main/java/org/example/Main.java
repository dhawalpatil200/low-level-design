package org.example;

import org.example.enums.LogLevel;
import org.example.strategies.appender.ConsoleAppender;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LoggerConfig config = new LoggerConfig(LogLevel.INFO, List.of(new ConsoleAppender()));
        LoggerFactory loggerFactory = LoggerFactory.getLoggerFactory();
        loggerFactory.initialize(config);


        Logger logger = loggerFactory.getLogger("com.samsung.sure");
        logger.debug("Hello debug log");
        logger.info("Hello info log");
        logger.warn("Hello warn log");
    }
}