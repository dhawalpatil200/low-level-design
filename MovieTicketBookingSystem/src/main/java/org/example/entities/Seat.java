package org.example.entities;

import org.example.strategy.PricingStrategy;

public class Seat {
    private String seatNumber;
    private PricingStrategy pricingStrategy;

    public Seat(String seatNumber, PricingStrategy pricingStrategy) {
        this.seatNumber = seatNumber;
        this.pricingStrategy = pricingStrategy;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }
}
