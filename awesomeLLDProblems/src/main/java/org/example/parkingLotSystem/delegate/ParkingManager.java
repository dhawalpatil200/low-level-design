package org.example.parkingLotSystem.delegate;

import org.example.parkingLotSystem.entities.ParkingSpot;
import org.example.parkingLotSystem.entities.Vehicle;
import org.example.parkingLotSystem.entities.VehicleSize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingManager {
    private final Map<VehicleSize, List<ParkingSpot>> availableSpots;
    private final Map<Vehicle, ParkingSpot> vehicleToSpotMap;

    public ParkingManager(Map<VehicleSize, List<ParkingSpot>> availableSpots) {
        this.availableSpots = availableSpots;
        this.vehicleToSpotMap = new HashMap<>();
    }

    public ParkingSpot findSpotForVehicle(Vehicle vehicle) {
        // smallest spot that a vehicle can fit in
        for(VehicleSize size: VehicleSize.values()) {
            if(size.ordinal() >= vehicle.getSize().ordinal()) {
                for(ParkingSpot spot: availableSpots.get(size)) {

                    if(spot.isAvailable()) {
                        return spot;
                    }
                }
            }
        }

        return  null; // No suitable spot
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = this.findSpotForVehicle(vehicle);
        if(spot != null) {
            spot.occupy(vehicle);
            vehicleToSpotMap.put(vehicle, spot);
            availableSpots.get(spot.getSize()).remove(spot);
            System.out.println("Vehicle parked v : " + vehicle.getLicencePlateNumber());
            return spot;
        }
        return null;
    }

    public void unparkVehicle(Vehicle vehicle) {
        ParkingSpot spot = vehicleToSpotMap.remove(vehicle);
        if(spot != null) {
            spot.vacate();
            availableSpots.get(spot.getSize()).add(spot);
        }
    }
}
