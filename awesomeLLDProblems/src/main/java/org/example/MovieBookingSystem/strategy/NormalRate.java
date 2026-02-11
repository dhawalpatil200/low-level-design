package org.example.MovieBookingSystem.strategy;

import java.math.BigDecimal;

public class NormalRate implements PricingStrategy{
    @Override
    public BigDecimal getPrice() {
        return BigDecimal.valueOf(300L);
    }
}
