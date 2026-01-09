package org.example.lockingExample;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Seat {
    private final AtomicInteger version = new AtomicInteger(0);
    private volatile boolean booked = false;

    public boolean book(int expectedVersion) {

        // atomic: verify version AND update version
        if (!version.compareAndSet(expectedVersion, expectedVersion + 1)) {
            return false;
        }

        // now this thread owns the update
        booked = true;
        return true;
    }

    public int getVersion() {
        return version.get();
    }
}