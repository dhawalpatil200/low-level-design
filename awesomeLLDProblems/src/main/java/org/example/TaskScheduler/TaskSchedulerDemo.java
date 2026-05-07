package org.example.TaskScheduler;

import org.example.TaskScheduler.observers.LoggingObserver;
import org.example.TaskScheduler.strategy.OneTimeSchedulingStrategy;
import org.example.TaskScheduler.strategy.RecurringSchedulingStrategy;
import org.example.TaskScheduler.strategy.SchedulingStrategy;

import java.time.Duration;
import java.time.LocalDateTime;

public class TaskSchedulerDemo {
    public static void main(String[] args) throws InterruptedException{
        TaskSchedulerService scheduler = TaskSchedulerService.getInstance();
        scheduler.addObserver(new LoggingObserver());

        scheduler.initialize(10);

        Task oneTimeTask = new PrintMessageTask("This is a one-time task.");
        SchedulingStrategy oneTimeStrategy = new OneTimeSchedulingStrategy(LocalDateTime.now().plusSeconds(1));

        Task recurringTask = new PrintMessageTask("This is a recurring task.");
        SchedulingStrategy recurringStrategy = new RecurringSchedulingStrategy(Duration.ofSeconds(2));


        Task backupTask = new DataBackupTask("/data/source", "/data/backup");
        SchedulingStrategy longRunningRecurringStrategy = new OneTimeSchedulingStrategy(LocalDateTime.now().plusSeconds(3));

        System.out.println("Scheduling tasks...");
        scheduler.schedule(oneTimeTask, oneTimeStrategy);
        scheduler.schedule(recurringTask, recurringStrategy);
        scheduler.schedule(backupTask, longRunningRecurringStrategy);

        System.out.println("Scheduler is running. Waiting for tasks to execute... (Demo will run for 10 seconds)");

        Thread.sleep(6000);
        scheduler.shutdown();
    }
}
