package org.example.parkingLotSystem.entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;

    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Ticket(Vehicle vehicle, ParkingSpot parkingSpot) {
        this.ticketId = generateRandomTicketId();
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
        this.exitTime = null;
    }

    public static String generateRandomTicketId() {
        return UUID.randomUUID().toString();
    }

    public BigDecimal calculateParkingDuration() {
        return new BigDecimal(Duration.between(entryTime,
                Objects.requireNonNullElseGet(exitTime, LocalDateTime::now)).toMinutes());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
}
