package org.example;

import org.example.entities.Direction;

public interface HallwayPanelSubject {
    void addObserver(ElevatorObserver observer);
    void removeObserver(ElevatorObserver observer);
    void notifyObservers(Direction direction);
}
