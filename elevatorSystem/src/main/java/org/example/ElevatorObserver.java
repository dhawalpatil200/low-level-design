package org.example;

import org.example.entities.Direction;

public interface ElevatorObserver {
    void update(int floor, Direction direction);
}
