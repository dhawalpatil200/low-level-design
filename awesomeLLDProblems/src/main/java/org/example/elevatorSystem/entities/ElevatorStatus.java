package org.example.elevatorSystem.entities;

public class ElevatorStatus {
    private int currentFloor;
    private Direction direction;

    public ElevatorStatus(int currentFloor, Direction up) {
        this.currentFloor = currentFloor;
        direction = Direction.IDLE;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
