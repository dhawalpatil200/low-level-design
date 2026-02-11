package org.example.elevatorSystem;

import org.example.elevatorSystem.dispatcher.DispatchingStrategy;
import org.example.elevatorSystem.dispatcher.ElevatorDispatch;
import org.example.elevatorSystem.dispatcher.ShortestSeekTimeFirstStrategy;
import org.example.elevatorSystem.entities.Direction;
import org.example.elevatorSystem.entities.ElevatorCar;
import org.example.elevatorSystem.observer.ElevatorSystem;
import org.example.elevatorSystem.observer.HallwayButtonPanel;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ElevatorSystemDemo {
    public static void main(String[] args) {

        // ------------------- Create Elevators --------------------

        List<ElevatorCar> elevators = List.of(
                new ElevatorCar(0),
                new ElevatorCar(5),
                new ElevatorCar(10)
        );

        // ------------------- Strategy ----------------------------

        DispatchingStrategy strategy =
                new ShortestSeekTimeFirstStrategy();

        ElevatorDispatch elevatorDispatch = new ElevatorDispatch(strategy);

        // ------------------- Elevator System ---------------------

        ElevatorSystem elevatorSystem =
                new ElevatorSystem(elevators, elevatorDispatch);

        // ------------------- Hallway Panels ----------------------

        HallwayButtonPanel floor2Panel = new HallwayButtonPanel(2);
        HallwayButtonPanel floor7Panel = new HallwayButtonPanel(7);
        HallwayButtonPanel floor12Panel = new HallwayButtonPanel(12);

        // Observer registration
        floor2Panel.addObserver(elevatorSystem);
        floor7Panel.addObserver(elevatorSystem);
        floor12Panel.addObserver(elevatorSystem);

        // ------------------- Scheduler ---------------------------

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {

            elevators.forEach(ElevatorCar::step);

            System.out.println("----- Elevator Status -----");
            for (int i = 0; i < elevators.size(); i++) {
                ElevatorCar car = elevators.get(i);
                System.out.printf("Elevator-%d | Floor: %d | Direction: %s%n",
                        i,
                        car.getCurrentFloor(),
                        car.getCurrentDirection());
            }

            System.out.println("---------------------------\n");

        }, 0, 1, TimeUnit.SECONDS);

        // ------------------- Simulated Requests ------------------

//        sleep(2);
        System.out.println(">> User presses UP at floor 2");
        floor2Panel.pressButton(Direction.UP);

        sleep(3);
        System.out.println(">> User presses DOWN at floor 12");
        floor12Panel.pressButton(Direction.DOWN);

        sleep(4);
        System.out.println(">> User presses UP at floor 7");
        floor7Panel.pressButton(Direction.UP);

        // Let simulation run for some time
        sleep(30);
        scheduler.shutdown();
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ignored) {
        }
    }
}