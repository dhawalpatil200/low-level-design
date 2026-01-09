package org.example.lockingExample;

class Seat1 {
    private boolean booked = false;

    // pessimistic: lock taken immediately
    public synchronized boolean book() {
        if (booked) {
            return false;
        }

        // simulate processing
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        booked = true;
        return true;
    }
}

