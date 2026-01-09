package org.example.entities;

import java.util.LinkedList;
import java.util.Queue;

public class ElevatorCar {
    private ElevatorStatus status;
    private final Queue<Integer> targetFloors;

    public ElevatorCar(int startingFloor) {
        this.status = new ElevatorStatus(startingFloor, Direction.IDLE);
        this.targetFloors = new LinkedList<>();
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    public Direction getCurrentDirection() {
        return status.getDirection();
    }

    public int getCurrentFloor() {
        return status.getCurrentFloor();
    }

    public void addFloorRequest(int floor) {
        if(!targetFloors.contains(floor)) {
            targetFloors.offer(floor);
            updateDirection(floor);
        }
    }

    public boolean isIdle() {
        return targetFloors.isEmpty();
    }

    private void updateDirection(int targetFloor) {
        if(status.getCurrentFloor() < targetFloor) {
            status = new ElevatorStatus(status.getCurrentFloor(), Direction.UP);
        } else if(status.getCurrentFloor() > targetFloor) {
            status = new ElevatorStatus(status.getCurrentFloor(), Direction.DOWN);
        }
    }
}
