package org.example.LoggingFramework;


import org.example.LoggingFramework.enums.LogLevel;
import org.example.LoggingFramework.strategies.appender.LogAppender;

import java.util.List;

public class LoggerConfig {
    private LogLevel level;
    private List<LogAppender> appenders;

    public LoggerConfig(LogLevel level, List<LogAppender> appenders) {
        this.level = level;
        this.appenders = appenders;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public List<LogAppender> getAppenders() {
        return appenders;
    }

    public void setAppenders(List<LogAppender> appenders) {
        this.appenders = appenders;
    }
}
