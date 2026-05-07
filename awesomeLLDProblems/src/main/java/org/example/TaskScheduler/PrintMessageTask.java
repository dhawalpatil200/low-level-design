package org.example.TaskScheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;

class PrintMessageTask implements Task {
    private final String message;

    public PrintMessageTask(String message) {
        this.message = message;
    }

    @Override
    public String getName() { return message; }

    @Override
    public void execute() {
        System.out.printf("[%s] %s%n", LocalTime.now().withNano(0), message);
    }
}