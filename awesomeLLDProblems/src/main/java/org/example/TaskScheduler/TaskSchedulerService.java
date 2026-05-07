package org.example.TaskScheduler;

import org.example.TaskScheduler.enums.TaskStatus;
import org.example.TaskScheduler.exceptions.TaskSchedulerException;
import org.example.TaskScheduler.observers.TaskExecutionObserver;
import org.example.TaskScheduler.strategy.SchedulingStrategy;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class TaskSchedulerService {
    private static volatile TaskSchedulerService instance;
    private static final Object instanceLock = new Object();

    private final PriorityQueue<ScheduledTask> taskQueue;  // Min-heap by execution time
    private final Object queueLock;     // Monitor object for wait/notify coordination
    private final List<TaskExecutionObserver> observers;

    private Thread[] workers;           // Raw thread pool (no ExecutorService)
    private volatile boolean running;   // volatile so all workers see shutdown immediately
    private final AtomicLong sequenceCounter;  // Generates tiebreaker sequence numbers

    private TaskSchedulerService() {
        this.taskQueue = new PriorityQueue<>();
        this.queueLock = new Object();
        // CopyOnWriteArrayList: safe to iterate while another thread adds observers
        this.observers = new CopyOnWriteArrayList<>();
        this.running = false;
        this.sequenceCounter = new AtomicLong(0);
    }

    public static TaskSchedulerService getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new TaskSchedulerService();
                }
            }
        }
        return instance;
    }

    public void initialize(int workerCount) {
        if (workerCount <= 0) {
            throw new IllegalArgumentException("Worker count must be positive");
        }
        if (running) {
            throw new TaskSchedulerException("Scheduler is already running");
        }

        this.running = true;
        this.workers = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new Thread(this::runWorker, "Scheduler-Worker-" + i);
            // Daemon threads don't prevent JVM shutdown. If the main thread
            // exits without calling shutdown(), the JVM still terminates cleanly.
            workers[i].setDaemon(true);
            workers[i].start();
        }
        System.out.printf("Started %d worker threads%n", workerCount);
    }

    public String schedule(Task task, SchedulingStrategy strategy) {
        if (task == null || strategy == null) {
            throw new IllegalArgumentException("Task and strategy must not be null");
        }
        if (!running) {
            throw new TaskSchedulerException("Scheduler is not running");
        }

        ScheduledTask scheduledTask = new ScheduledTask(task, strategy, sequenceCounter.getAndIncrement());

        synchronized (queueLock) {
            taskQueue.add(scheduledTask);
            queueLock.notifyAll();
        }
        return scheduledTask.getId();
    }

    public boolean cancel(String taskId) {
        synchronized (queueLock) {
            Iterator<ScheduledTask> iterator = taskQueue.iterator();
            while (iterator.hasNext()) {
                ScheduledTask task = iterator.next();
                if (task.getId().equals(taskId)) {
                    task.setStatus(TaskStatus.CANCELLED);
                    iterator.remove();
                    return true;
                }
            }
        }
        // Task not found: either already executed or invalid ID
        return false;
    }

    public void addObserver(TaskExecutionObserver observer) {
        // CopyOnWriteArrayList handles thread safety internally
        observers.add(observer);
    }

    public void shutdown() {
        running = false;  // volatile write: immediately visible to all workers

        // Wake any workers blocked in wait()
        synchronized (queueLock) {
            queueLock.notifyAll();
        }

        // Interrupt workers that might be sleeping in wait(delayMs)
        // or blocked inside a long-running task
        for (Thread worker : workers) {
            if (worker != null) {
                worker.interrupt();
            }
        }
        System.out.println("Scheduler shut down.");
    }

    private void runWorker() {
        while (running) {
            ScheduledTask task = null;

            synchronized (queueLock) {
                while (running) {
                    if(taskQueue.isEmpty()) {
                        try {
                            queueLock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                        continue;
                    }

                    ScheduledTask next = taskQueue.peek();
                    long delayMs = Duration.between(LocalDateTime.now(), next.getNextExecutionTime()).toMillis();
                    if(delayMs <= 0) {
                        task = taskQueue.poll();
                        break;
                    } else {
                        // try to sleep for delayMs
                        try {
                            queueLock.wait(delayMs);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
            }

            if(task != null && task.getStatus() != TaskStatus.CANCELLED) {
                executeTask(task);
            }
        }
    }

    private void executeTask(ScheduledTask scheduledTask) {
        Task task = scheduledTask.getTask();
        scheduledTask.setStatus(TaskStatus.RUNNING);
        onTaskStarted(scheduledTask);

        try {
            task.execute();
            scheduledTask.setStatus(TaskStatus.COMPLETED);
            onTaskCompleted(scheduledTask);
        } catch (Exception e) {
            scheduledTask.setStatus(TaskStatus.FAILED);
            onTaskFailed(scheduledTask, e);
        }

        // check if task is eligible for next execution if yes then scheduled it
        scheduledTask.updateForNextExecution();
        LocalDateTime nextExecutionTime = scheduledTask.getNextExecutionTime();
        if(nextExecutionTime != null) {
            scheduledTask.setStatus(TaskStatus.SCHEDULED);
            synchronized (queueLock) {
                taskQueue.add(scheduledTask);
                queueLock.notifyAll();
            }
        }
    }

    private void onTaskStarted(ScheduledTask task) {
        for(TaskExecutionObserver observer: observers) {
            try {
                observer.onTaskStarted(task);
            } catch (Exception e) {
                // observer failure shouldn't impact task execution
            }

        }
    }

    private void onTaskCompleted(ScheduledTask task) {
        for(TaskExecutionObserver observer: observers) {
            try {
                observer.onTaskCompleted(task);
            } catch (Exception e) {
                // observer failure shouldn't impact task execution
            }

        }
    }

    private void onTaskFailed(ScheduledTask task, Exception taskException) {
        for(TaskExecutionObserver observer: observers) {
            try {
                observer.onTaskFailed(task, taskException);
            } catch (Exception e) {
                // observer failure shouldn't impact task execution
            }

        }
    }
}
