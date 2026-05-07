package org.example.TaskScheduler.exceptions;

public class TaskSchedulerException extends RuntimeException{
    public TaskSchedulerException(String message) {
        super(message);
    }

    public TaskSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}
