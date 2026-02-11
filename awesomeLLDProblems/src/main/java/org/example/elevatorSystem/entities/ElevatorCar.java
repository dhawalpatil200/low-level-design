package org.example.elevatorSystem.entities;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.UUID;

public class ElevatorCar {

    private final ElevatorStatus status;
    private String id;

    // Floors above current floor → lowest first
    private final PriorityQueue<Integer> upQueue =
            new PriorityQueue<>();

    // Floors below current floor → highest first
    private final PriorityQueue<Integer> downQueue =
            new PriorityQueue<>(Comparator.reverseOrder());

    public ElevatorCar(int startingFloor) {
        this.status = new ElevatorStatus(startingFloor, Direction.IDLE);
        id = Integer.toString(startingFloor);
        System.out.println(String.format("[INIT_ELEVATOR][floor=%s][id=%s]", status.getCurrentFloor(), id));
    }

    public String getId() {
        return id;
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    public int getCurrentFloor() {
        return status.getCurrentFloor();
    }

    public Direction getCurrentDirection() {
        return status.getDirection();
    }

    public boolean isIdle() {
        return upQueue.isEmpty() && downQueue.isEmpty();
    }

    /**
     * Adds a new floor request into appropriate queue.
     */
    public synchronized void addFloorRequest(int floor) {

        int current = status.getCurrentFloor();

        if (floor == current) {
            return; // already here
        }

        if (floor > current) {
            upQueue.offer(floor);
        } else {
            downQueue.offer(floor);
        }

        updateDirectionIfIdle();
    }

    /**
     * Simulates one movement tick of the elevator.
     * Should be called by scheduler periodically.
     */
    public synchronized void step() {

        if (isIdle()) {
            status.setDirection(Direction.IDLE);
            return;
        }

        updateDirectionIfNeeded();

        int current = status.getCurrentFloor();

        if (status.getDirection() == Direction.UP) {
            moveUp(current);
        }
        else if (status.getDirection() == Direction.DOWN) {
            moveDown(current);
        }
    }

    // ------------------ Internal helpers -----------------------

    private void moveUp(int current) {
        int nextTarget = upQueue.peek();

        if (current < nextTarget) {
            status.setCurrentFloor(current + 1);
        }

        if (status.getCurrentFloor() == nextTarget) {
            upQueue.poll(); // reached target
        }
    }

    private void moveDown(int current) {
        int nextTarget = downQueue.peek();

        if (current > nextTarget) {
            status.setCurrentFloor(current - 1);
        }

        if (status.getCurrentFloor() == nextTarget) {
            downQueue.poll(); // reached target
        }
    }

    /**
     * If elevator was IDLE, decide initial direction.
     */
    private void updateDirectionIfIdle() {
        if (status.getDirection() != Direction.IDLE) {
            return;
        }

        if (!upQueue.isEmpty()) {
            status.setDirection(Direction.UP);
        }
        else if (!downQueue.isEmpty()) {
            status.setDirection(Direction.DOWN);
        }
    }

    /**
     * Handles direction switching when current queue becomes empty.
     */
    private void updateDirectionIfNeeded() {

        if (status.getDirection() == Direction.UP && upQueue.isEmpty()) {
            if (!downQueue.isEmpty()) {
                status.setDirection(Direction.DOWN);
            } else {
                status.setDirection(Direction.IDLE);
            }
        }

        else if (status.getDirection() == Direction.DOWN && downQueue.isEmpty()) {
            if (!upQueue.isEmpty()) {
                status.setDirection(Direction.UP);
            } else {
                status.setDirection(Direction.IDLE);
            }
        }
    }
}
