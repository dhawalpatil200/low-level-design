package org.example.entities;

public class CompactParkingSpot implements ParkingSpot{
    private int spotNumber;
    private Vehicle vehicle;

    public CompactParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    @Override
    public boolean isAvailable() {
        return vehicle == null;
    }

    @Override
    public void occupy(Vehicle vehicle) {
        if(this.vehicle == null) {
            this.vehicle = vehicle;
        } else {
            System.out.println("Spot is already occupied");
        }
    }

    @Override
    public void vacate() {
        this.vehicle = null;
    }

    @Override
    public int getSpotNumber() {
        return spotNumber;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL;
    }
}
