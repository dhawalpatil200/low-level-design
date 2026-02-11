package org.example.GroceryStoreSystem.discount;

import java.math.BigDecimal;

public interface DiscountCalculationStrategy {
    BigDecimal calculateDiscountedPrice(BigDecimal originalPrice);
}
