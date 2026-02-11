package org.example.elevatorSystem.dispatcher;


import org.example.elevatorSystem.entities.Direction;
import org.example.elevatorSystem.entities.ElevatorCar;
import org.example.elevatorSystem.entities.ElevatorStatus;

import java.util.List;

public interface DispatchingStrategy {
    ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction);
}
