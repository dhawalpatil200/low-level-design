package org.example.dispatcher;

import org.example.entities.Direction;
import org.example.entities.ElevatorCar;

import java.util.List;

public class ShortestSeekTimeFirstStrategy implements DispatchingStrategy{
    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction) {
        ElevatorCar bestElevator = null;
        int shortestDistance = Integer.MAX_VALUE;

        for(ElevatorCar elevator: elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            if(elevator.isIdle() || elevator.getCurrentDirection() == direction && distance < shortestDistance) {
                bestElevator = elevator;
                shortestDistance = distance;
            }
        }
        return bestElevator;
    }
}
