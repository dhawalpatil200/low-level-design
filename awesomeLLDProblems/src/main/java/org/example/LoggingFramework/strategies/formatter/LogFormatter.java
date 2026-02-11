package org.example.LoggingFramework.strategies.formatter;


import org.example.LoggingFramework.entities.LogMessage;

public interface LogFormatter {
    String format(LogMessage message);
}
