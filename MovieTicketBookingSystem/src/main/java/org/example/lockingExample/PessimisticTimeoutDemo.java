package org.example.lockingExample;

public class PessimisticTimeoutDemo {
    public static void main(String[] args) {

        Seat2 seat = new Seat2();

        Runnable task = () -> {
            boolean success = seat.book(20000); // wait max 200 ms
            System.out.println(Thread.currentThread().getName()
                    + " result: " + success);
        };

        for (int i = 0; i < 5; i++) {
            new Thread(task).start();
        }
    }
}
