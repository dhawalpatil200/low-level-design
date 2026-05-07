package org.example.TaskScheduler;

import org.example.TaskScheduler.enums.TaskStatus;
import org.example.TaskScheduler.strategy.SchedulingStrategy;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class ScheduledTask implements Comparable<ScheduledTask>{
    private final String id;
    private final Task task;
    private final SchedulingStrategy strategy;
    private LocalDateTime nextExecutionTime;
    private LocalDateTime lastExecutionTime;
    private TaskStatus status;
    private final long sequenceNumber;

    public ScheduledTask(Task task, SchedulingStrategy strategy, long sequenceNumber) {
        this.id = UUID.randomUUID().toString();
        this.task = task;
        this.strategy = strategy;
        this.sequenceNumber = sequenceNumber;
        this.lastExecutionTime = null;
        this.status = TaskStatus.SCHEDULED;

        Optional<LocalDateTime> firstTime = strategy.getNextExecutionTime(null);
        this.nextExecutionTime = firstTime.orElse(null);
    }

    public String getId() { return id; }
    public Task getTask() { return task; }
    public LocalDateTime getNextExecutionTime() { return nextExecutionTime; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public boolean hasMoreExecutions() {
        return strategy.getNextExecutionTime(lastExecutionTime).isPresent();
    }

    public void updateForNextExecution() {
        this.lastExecutionTime = LocalDateTime.now();
        Optional<LocalDateTime> nextTime = strategy.getNextExecutionTime(lastExecutionTime);
        this.nextExecutionTime = nextTime.orElse(null);
    }

    @Override
    public String toString() {
        return String.format("ScheduledTask[%s, next=%s, status=%s]",
                task.getName(), nextExecutionTime, status);
    }

    @Override
    public int compareTo(ScheduledTask other) {
        if(this.nextExecutionTime == null && other.nextExecutionTime == null) return 0;
        if(this.nextExecutionTime == null) return 1;
        if(other.nextExecutionTime == null) return -1;

        int timeCompare = this.nextExecutionTime.compareTo(other.nextExecutionTime);
        if(timeCompare != 0) return timeCompare;

        return Long.compare(this.sequenceNumber, other.sequenceNumber);
    }
}
