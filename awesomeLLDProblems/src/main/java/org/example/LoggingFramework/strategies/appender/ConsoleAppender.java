package org.example.LoggingFramework.strategies.appender;

import org.example.LoggingFramework.entities.LogMessage;
import org.example.LoggingFramework.strategies.formatter.LogFormatter;
import org.example.LoggingFramework.strategies.formatter.SimpleTextFormatter;

public class ConsoleAppender implements LogAppender{
    private final LogFormatter formatter;

    public ConsoleAppender(SimpleTextFormatter logFormatter) {
        this.formatter = logFormatter;
    }

    @Override
    public void append(LogMessage logMessage) {
        System.out.println(formatter.format(logMessage));
    }
}
