package org.example.dispatcher;

import org.example.entities.Direction;
import org.example.entities.ElevatorCar;

import java.util.List;

public class ElevatorDispatch {
    private final DispatchingStrategy strategy;

    public ElevatorDispatch(DispatchingStrategy strategy) {
        this.strategy = strategy;
    }

    public void dispatchElevatorCar(int floor, Direction direction, List<ElevatorCar> elevators) {
        ElevatorCar selectedElevator = strategy.selectElevator(elevators, floor, direction);
        System.out.println(String.format("[floor=%s][direction=%s][elevator=%s]", floor, direction, selectedElevator.getId()));
        if(selectedElevator != null) {
            selectedElevator.addFloorRequest(floor);
        }
    }
}
