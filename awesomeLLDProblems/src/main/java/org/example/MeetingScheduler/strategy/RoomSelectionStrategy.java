package org.example.MeetingScheduler.strategy;

import org.example.MeetingScheduler.entities.Room;

import java.util.List;

public interface RoomSelectionStrategy {
    Room selectRoom(List<Room> availableRooms, int requiredCapacity);
}