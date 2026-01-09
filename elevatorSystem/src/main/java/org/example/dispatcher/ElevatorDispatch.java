package org.example.dispatcher;

import org.example.ElevatorObserver;
import org.example.entities.Direction;
import org.example.entities.ElevatorCar;

import java.util.List;

public class ElevatorDispatch implements ElevatorObserver {
    private final DispatchingStrategy strategy;

    public ElevatorDispatch(DispatchingStrategy strategy) {
        this.strategy = strategy;
    }

    public void dispatchElevatorCar(int floor, Direction direction, List<ElevatorCar> elevators) {
        ElevatorCar selectedElevator = strategy.selectElevator(elevators, floor, direction);
        if(selectedElevator != null) {
            selectedElevator.addFloorRequest(floor);
        }
    }

    @Override
    public void update(int floor, Direction direction) {
//        dispatchElevatorCar(floor, direction);
    }
}
