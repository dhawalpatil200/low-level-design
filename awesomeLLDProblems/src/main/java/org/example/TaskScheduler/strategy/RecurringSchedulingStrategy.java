package org.example.TaskScheduler.strategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class RecurringSchedulingStrategy implements SchedulingStrategy{
    private final Duration interval;

    public RecurringSchedulingStrategy(Duration interval) {
        if(interval.isNegative() || interval.isZero()) {
            throw new IllegalArgumentException("Duration can't be negative");
        }

        this.interval = interval;
    }

    @Override
    public Optional<LocalDateTime> getNextExecutionTime(LocalDateTime lastExecutionTime) {
        LocalDateTime baseTime = (lastExecutionTime != null ? lastExecutionTime : LocalDateTime.now());
        return Optional.of(baseTime.plus(interval));
    }
}
