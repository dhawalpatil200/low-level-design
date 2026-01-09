package org.example.delegate.FareStrategy;

import org.example.entities.Ticket;

import java.math.BigDecimal;

public interface FareStrategy {
    BigDecimal calculateFare(Ticket ticket, BigDecimal inputFare);
}
