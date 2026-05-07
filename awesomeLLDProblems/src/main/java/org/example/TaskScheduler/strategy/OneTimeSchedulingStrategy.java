package org.example.TaskScheduler.strategy;

import java.time.LocalDateTime;
import java.util.Optional;

public class OneTimeSchedulingStrategy implements SchedulingStrategy{
    private final LocalDateTime executionTime;

    public OneTimeSchedulingStrategy(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public Optional<LocalDateTime> getNextExecutionTime(LocalDateTime lastExecutionTime) {
        if (lastExecutionTime == null) {
            return Optional.of(executionTime);
        }
        return Optional.empty();
    }
}
