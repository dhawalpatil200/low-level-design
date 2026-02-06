package org.example.strategies.appender;

import org.example.entities.LogMessage;
import org.example.strategies.formatter.LogFormatter;
import org.example.strategies.formatter.SimpleTextFormatter;

public class ConsoleAppender implements LogAppender{
    private final LogFormatter formatter;

    public ConsoleAppender(LogFormatter logFormatter) {
        this.formatter = logFormatter;
    }

    @Override
    public void append(LogMessage logMessage) {
        System.out.println(formatter.format(logMessage));
    }
}
