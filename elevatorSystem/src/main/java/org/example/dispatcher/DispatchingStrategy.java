package org.example.dispatcher;

import org.example.entities.Direction;
import org.example.entities.ElevatorCar;

import java.util.List;

public interface DispatchingStrategy {
    ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction);
}
