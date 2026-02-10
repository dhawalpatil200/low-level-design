package org.example.strategies.formatter;

import org.example.entities.LogMessage;

public interface LogFormatter {
    String format(LogMessage message);
}
