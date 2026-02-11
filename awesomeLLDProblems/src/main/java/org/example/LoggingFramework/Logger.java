package org.example.LoggingFramework;


import org.example.LoggingFramework.entities.LogMessage;
import org.example.LoggingFramework.enums.LogLevel;
import org.example.LoggingFramework.strategies.appender.LogAppender;

import java.util.List;

public class Logger {
    private final String name;
    private final LogLevel level;
    private final List<LogAppender> appender;

    public Logger(String name, List<LogAppender> appender, LogLevel level) {
        this.name = name;
        this.appender = appender;
        this.level = level;
    }

    public void log(LogLevel messageLevel, String message) {
        if (messageLevel.isGreaterOrEqual(this.level)) {
            LogMessage logMessage = new LogMessage(messageLevel, this.name, message);
            for(LogAppender appender: appender) {
                appender.append(logMessage);
            }
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
    public void info(String message) {
        log(LogLevel.INFO, message);
    }
    public void warn(String message) {
        log(LogLevel.WARN, message);
    }
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
}
