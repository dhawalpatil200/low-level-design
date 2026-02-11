package org.example.parkingLotSystem.delegate.FareStrategy;

import org.example.parkingLotSystem.entities.Ticket;

import java.math.BigDecimal;

public class BaseFareStrategy implements FareStrategy{
    private static final BigDecimal SMALL_VEHICLE_RATE = new BigDecimal("1.0");
    private static final BigDecimal MEDIUM_VEHICLE_RATE = new BigDecimal("2.0");
    private static final BigDecimal LARGE_VEHICLE_RATE = new BigDecimal("3.0");
    @Override
    public BigDecimal calculateFare(Ticket ticket, BigDecimal inputFare) {
        BigDecimal fare = inputFare;
        BigDecimal rate = switch (ticket.getVehicle().getSize()) {
            case MEDIUM -> MEDIUM_VEHICLE_RATE;
            case LARGE -> LARGE_VEHICLE_RATE;
            default -> SMALL_VEHICLE_RATE;
        };

        fare = fare.add(rate.multiply(ticket.calculateParkingDuration()));
        return fare;
    }
}
