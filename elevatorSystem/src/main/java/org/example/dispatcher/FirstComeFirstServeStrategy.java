package org.example.dispatcher;

import org.example.entities.Direction;
import org.example.entities.ElevatorCar;

import java.util.List;

public class FirstComeFirstServeStrategy implements DispatchingStrategy{
    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction) {
        for(ElevatorCar elevator: elevators) {
            if(elevator.isIdle() || elevator.getCurrentDirection() == direction) {
                return elevator;
            }
        }
        return elevators.get((int)(Math.random() * elevators.size()));
    }
}
