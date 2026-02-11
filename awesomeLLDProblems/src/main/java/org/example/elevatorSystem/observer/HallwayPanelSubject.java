package org.example.elevatorSystem.observer;


import org.example.elevatorSystem.entities.Direction;

public interface HallwayPanelSubject {
    void addObserver(ElevatorObserver observer);
    void removeObserver(ElevatorObserver observer);
    void notifyObservers(Direction direction);
}
