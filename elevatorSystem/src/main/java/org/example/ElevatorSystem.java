package org.example;

import org.example.dispatcher.ElevatorDispatch;
import org.example.entities.Direction;
import org.example.entities.ElevatorCar;
import org.example.entities.ElevatorStatus;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {
    private final List<ElevatorCar> elevators;
    private final ElevatorDispatch elevatorDispatcher;

    public ElevatorSystem(List<ElevatorCar> elevators, ElevatorDispatch elevatorDispatch) {
        this.elevators = elevators;
        this.elevatorDispatcher = elevatorDispatch;
    }

    public List<ElevatorStatus> getAllElevatorStatuses() {
        List<ElevatorStatus> statuses = new ArrayList<>();
        for(ElevatorCar elevator: elevators) {
            statuses.add(elevator.getStatus());
        }
        return statuses;
    }

    public void requestElevator(int currentFloor, Direction direction) {
        elevatorDispatcher.dispatchElevatorCar(currentFloor, direction, elevators);
    }

    public void selectFloor(ElevatorCar car, int destinationFloor) {
        car.addFloorRequest(destinationFloor);
    }
}
