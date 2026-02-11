package org.example.parkingLotSystem.delegate.FareStrategy;

import org.example.parkingLotSystem.entities.Ticket;

import java.math.BigDecimal;

public interface FareStrategy {
    BigDecimal calculateFare(Ticket ticket, BigDecimal inputFare);
}
