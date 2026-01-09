package org.example.entities;

public class Room {
    private String roomNumber;
    private Layout layout;

    public Room(String roomNumber, Layout layout) {
        this.roomNumber = roomNumber;
        this.layout = layout;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Layout getLayout() {
        return layout;
    }
}
