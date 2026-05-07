package org.example.TaskScheduler.strategy;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SchedulingStrategy {
    Optional<LocalDateTime> getNextExecutionTime(LocalDateTime lastExecutionTime);
}
