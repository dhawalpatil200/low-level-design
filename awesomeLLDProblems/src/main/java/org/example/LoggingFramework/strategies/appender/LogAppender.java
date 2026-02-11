package org.example.LoggingFramework.strategies.appender;


import org.example.LoggingFramework.entities.LogMessage;

public interface LogAppender {
    void append(LogMessage logMessage);
}
