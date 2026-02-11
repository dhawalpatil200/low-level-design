package org.example.elevatorSystem.observer;

import org.example.elevatorSystem.entities.Direction;

import java.util.ArrayList;
import java.util.List;

public class HallwayButtonPanel implements HallwayPanelSubject {
    private final int floor;
    private List<ElevatorObserver> observers;

    public HallwayButtonPanel(int floor) {
        this.floor = floor;
        observers = new ArrayList<>();
    }

    public void pressButton(Direction direction) {
        notifyObservers(direction);
    }


    @Override
    public void addObserver(ElevatorObserver observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }

    }

    @Override
    public void removeObserver(ElevatorObserver observer) {
        int index = observers.indexOf(observer);
        if(index >= 0) {
            observers.remove(index);
        }
    }

    @Override
    public void notifyObservers(Direction direction) {
        for(ElevatorObserver elevatorObserver: observers) {
            elevatorObserver.update(floor, direction);
        }
    }
}
