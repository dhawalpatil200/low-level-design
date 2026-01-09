package org.example.discount;

import java.math.BigDecimal;

public interface DiscountCalculationStrategy {
    BigDecimal calculateDiscountedPrice(BigDecimal originalPrice);
}
