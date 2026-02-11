package org.example.elevatorSystem.observer;

import org.example.elevatorSystem.entities.*;

public interface ElevatorObserver {
    void update(int floor, Direction direction);
}
