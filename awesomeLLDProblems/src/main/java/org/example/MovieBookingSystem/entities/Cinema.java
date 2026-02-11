package org.example.MovieBookingSystem.entities;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private String name;
    private City city;
    private List<Room> rooms;

    public Cinema(City city, String name) {
        this.city = city;
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
