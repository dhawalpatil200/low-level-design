package org.example.parkingLotSystem;

import org.example.parkingLotSystem.delegate.FareCalculator;
import org.example.parkingLotSystem.delegate.ParkingManager;
import org.example.parkingLotSystem.entities.ParkingSpot;
import org.example.parkingLotSystem.entities.Ticket;
import org.example.parkingLotSystem.entities.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingLot {
    private final ParkingManager parkingManager;
    private final FareCalculator fareCalculator;

    public ParkingLot(ParkingManager parkingManager, FareCalculator fareCalculator) {
        this.parkingManager = parkingManager;
        this.fareCalculator = fareCalculator;
    }

    public Ticket enterVehicle(Vehicle vehicle) {
        ParkingSpot spot = parkingManager.parkVehicle(vehicle);
        if(spot != null) {
            return new Ticket(vehicle, spot);
        } else {
            System.out.println("No available spots for vehicle: " + vehicle.getLicencePlateNumber());
        }
        return null;
    }

    public void leaveVehicle(Ticket ticket) {
        if(ticket != null && ticket.getExitTime() == null) {
            ticket.setExitTime(LocalDateTime.now());
            parkingManager.unparkVehicle(ticket.getVehicle());
            BigDecimal fare = fareCalculator.calculateFare(ticket);
            System.out.println("Vehicle " + ticket.getVehicle().getLicencePlateNumber() + " has left. Total fare: " + fare);
        } else {
            System.out.println("Invalid ticket or vehicle already exited");
        }
    }
}
