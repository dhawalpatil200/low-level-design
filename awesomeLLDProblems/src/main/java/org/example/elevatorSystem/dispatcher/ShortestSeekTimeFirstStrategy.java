package org.example.elevatorSystem.dispatcher;


import org.example.elevatorSystem.entities.Direction;
import org.example.elevatorSystem.entities.ElevatorCar;

import java.util.List;

public class ShortestSeekTimeFirstStrategy implements DispatchingStrategy{
    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, int requestFloor, Direction requestDirection) {

        ElevatorCar best = null;
        int minDistance = Integer.MAX_VALUE;

        // -------- Phase 1: Direction aware selection --------
        for (ElevatorCar elevator : elevators) {

            int elevatorFloor = elevator.getCurrentFloor();
            Direction elevatorDir = elevator.getCurrentDirection();

            boolean eligible = false;

            if (requestDirection == Direction.UP) {
                eligible =
                        elevatorFloor <= requestFloor &&
                                (elevatorDir == Direction.UP || elevatorDir == Direction.IDLE);
            }
            else if (requestDirection == Direction.DOWN) {
                eligible =
                        elevatorFloor >= requestFloor &&
                                (elevatorDir == Direction.DOWN || elevatorDir == Direction.IDLE);
            }

            if (!eligible) continue;

            int distance = Math.abs(elevatorFloor - requestFloor);

            if (distance < minDistance) {
                minDistance = distance;
                best = elevator;
            }
        }

        // -------- Phase 2: Fallback (pick nearest) --------
        if (best != null) return best;

        for (ElevatorCar elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - requestFloor);
            if (distance < minDistance) {
                minDistance = distance;
                best = elevator;
            }
        }

        return best;
    }

}
