package org.example.TaskScheduler.observers;

import org.example.TaskScheduler.ScheduledTask;

public interface TaskExecutionObserver {
    void onTaskStarted(ScheduledTask task);
    void onTaskCompleted(ScheduledTask task);
    void onTaskFailed(ScheduledTask task, Exception exception);
}
