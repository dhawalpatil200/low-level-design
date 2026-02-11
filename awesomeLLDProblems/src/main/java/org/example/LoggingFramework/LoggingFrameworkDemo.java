package org.example.LoggingFramework;



import org.example.LoggingFramework.enums.LogLevel;
import org.example.LoggingFramework.strategies.appender.ConsoleAppender;
import org.example.LoggingFramework.strategies.formatter.SimpleTextFormatter;

import java.util.List;

public class LoggingFrameworkDemo {
    public static void main(String[] args) {
        LoggerConfig config = new LoggerConfig(LogLevel.INFO, List.of(new ConsoleAppender(new SimpleTextFormatter())));
        LoggerFactory loggerFactory = LoggerFactory.getLoggerFactory();
        loggerFactory.initialize(config);


        Logger logger = loggerFactory.getLogger("com.samsung.sure");
        logger.debug("Hello debug log");
        logger.info("Hello info log");
        logger.warn("Hello warn log");
    }
}