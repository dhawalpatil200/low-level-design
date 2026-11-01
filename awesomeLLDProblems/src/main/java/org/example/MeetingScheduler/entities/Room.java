package org.example.MeetingScheduler.entities;

import org.example.MeetingScheduler.enums.RoomType;

public class Room {
    private final String id;
    private final String name;
    private final RoomType roomType;
    private final int capacity;

    public Room(String id, String name, RoomType roomType, int capacity) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
        this.capacity = capacity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public RoomType getRoomType() { return roomType; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return name + " (" + roomType + ", capacity: " + capacity + ")";
    }
}