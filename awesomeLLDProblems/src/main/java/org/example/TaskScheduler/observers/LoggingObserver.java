package org.example.TaskScheduler.observers;

import org.example.TaskScheduler.ScheduledTask;

public class LoggingObserver implements TaskExecutionObserver{
    @Override
    public void onTaskStarted(ScheduledTask task) {
        System.out.printf("[%s] Task '%s' started%n", Thread.currentThread().getName(), task.getTask().getName());
    }

    @Override
    public void onTaskCompleted(ScheduledTask task) {
        System.out.printf("[%s] Task '%s' completed%n", Thread.currentThread().getName(), task.getTask().getName());
    }

    @Override
    public void onTaskFailed(ScheduledTask task, Exception exception) {
        System.err.printf("[%s] Task '%s' failed: %s%n", Thread.currentThread().getName(), task.getTask().getName(), exception.getMessage());
    }
}
