package org.example.lockingExample;

public class OptimisticDemo {

    public static void main(String[] args) {

        Seat seat = new Seat();

        Runnable task = () -> {
            int v = seat.getVersion();   // read version
            boolean success = seat.book(v);
            System.out.println(Thread.currentThread().getName()
                    + " booking result: " + success);
        };

        for (int i = 0; i < 5; i++) {
            new Thread(task).start();
        }
    }
}

