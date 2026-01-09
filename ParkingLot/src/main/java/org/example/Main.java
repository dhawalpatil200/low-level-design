package org.example;

import org.example.delegate.FareCalculator;
import org.example.delegate.FareStrategy.BaseFareStrategy;
import org.example.delegate.FareStrategy.FareStrategy;
import org.example.delegate.ParkingManager;
import org.example.entities.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ParkingSpot parkingSpot1 = new CompactParkingSpot(202);
        Map<VehicleSize, List<ParkingSpot>> availableSpots = new HashMap<>();
        availableSpots.put(VehicleSize.SMALL, new ArrayList<>());
        availableSpots.put(VehicleSize.MEDIUM, new ArrayList<>());
        availableSpots.put(VehicleSize.LARGE, new ArrayList<>());
        availableSpots.get(VehicleSize.SMALL).add(parkingSpot1);
        ParkingManager parkingManager = new ParkingManager(availableSpots);

        List<FareStrategy> fareStrategyList = new ArrayList<>();
        fareStrategyList.add(new BaseFareStrategy());
        FareCalculator fareCalculator = new FareCalculator(fareStrategyList);

        ParkingLot parkingLot = new ParkingLot(parkingManager, fareCalculator);

        Vehicle vehicle1 = new MotorCycle("123");
        Vehicle vehicle2 = new MotorCycle("234");

        Ticket ticket1 = parkingLot.enterVehicle(vehicle1);
        Ticket ticket2 = parkingLot.enterVehicle(vehicle2);
        parkingLot.leaveVehicle(ticket1);
        ticket2 = parkingLot.enterVehicle(vehicle2);
    }
}