package org.example.lockingExample;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class Seat2 {

    private boolean booked = false;
    private final ReentrantLock lock = new ReentrantLock();

    public boolean book(long timeoutMs) {

        boolean acquired = false;
        try {
            acquired = lock.tryLock(timeoutMs, TimeUnit.MILLISECONDS);

            if (!acquired) {
                System.out.println("Could not acquire lock within timeout");
                return false;
            }

            if (booked) {
                return false;
            }

            // simulate processing
            try { Thread.sleep(10000); } catch (InterruptedException e) {}

            booked = true;
            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;

        } finally {
            if (acquired) {
                lock.unlock();
            }
        }
    }
}

