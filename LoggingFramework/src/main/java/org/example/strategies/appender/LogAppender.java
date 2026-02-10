package org.example.strategies.appender;

import org.example.entities.LogMessage;

public interface LogAppender {
    void append(LogMessage logMessage);
}
