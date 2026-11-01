package org.example.MeetingScheduler.strategy;

import org.example.MeetingScheduler.entities.Room;

import java.util.Comparator;
import java.util.List;

public class BestFitStrategy implements RoomSelectionStrategy {
    @Override
    public Room selectRoom(List<Room> availableRooms, int requiredCapacity) {
        return availableRooms.stream()
                .min(Comparator.comparingInt(Room::getCapacity))
                .orElse(null);
    }
}
