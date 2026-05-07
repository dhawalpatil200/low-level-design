package org.example.TaskScheduler;

import java.time.LocalTime;

public class DataBackupTask implements Task{
    private final String source;
    private final String destination;

    public DataBackupTask(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String getName() {
        return "DataBackupTask";
    }

    @Override
    public void execute() {
        System.out.printf("[%s] Starting backup: %s -> %s%n", LocalTime.now().withNano(0), source, destination);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("[%s] Backup completed: %s -> %s%n", LocalTime.now().withNano(0), source, destination);
    }
}
