package org.example.lockingExample;

public class PessimisticDemo {

    public static void main(String[] args) throws InterruptedException {

        Seat1 seat = new Seat1();

        Runnable task = () -> {
            boolean success = seat.book();
            System.out.println(Thread.currentThread().getName()
                    + " booking result: " + success);
        };

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task);
            thread.start();
            thread.join();
        }
    }
}

