package org.example.parkingLotSystem.delegate;

import org.example.parkingLotSystem.delegate.FareStrategy.FareStrategy;
import org.example.parkingLotSystem.entities.Ticket;

import java.math.BigDecimal;
import java.util.List;

public class FareCalculator {
    private final List<org.example.parkingLotSystem.delegate.FareStrategy.FareStrategy> fareStrategies;

    public FareCalculator(List<FareStrategy> fareStrategies) {
        this.fareStrategies = fareStrategies;
    }

    public BigDecimal calculateFare(Ticket ticket) {
        BigDecimal fare = BigDecimal.ZERO;
        for(FareStrategy strategy: fareStrategies) {
            fare = strategy.calculateFare(ticket, fare);
        }

        return fare;
    }
}
