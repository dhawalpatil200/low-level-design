package org.example.MeetingScheduler.strategy;

import org.example.MeetingScheduler.entities.Room;

import java.util.List;

public class FirstAvailableStrategy implements RoomSelectionStrategy {
    @Override
    public Room selectRoom(List<Room> availableRooms, int requiredCapacity) {
        return availableRooms.isEmpty() ? null : availableRooms.get(0);
    }
}
