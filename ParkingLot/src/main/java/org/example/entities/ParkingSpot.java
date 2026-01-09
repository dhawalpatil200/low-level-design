package org.example.entities;

public interface ParkingSpot {
    boolean isAvailable();
    void occupy(Vehicle vehicle);
    void vacate();
    int getSpotNumber();
    VehicleSize getSize();
}
