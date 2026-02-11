package org.example.GroceryStoreSystem.discount;


import org.example.GroceryStoreSystem.entities.Item;
import org.example.GroceryStoreSystem.entities.OrderItem;

import java.math.BigDecimal;

public class DiscountCampaign {
    private final String discountId;
    private final String name;
    private final DiscountCriteria discountCriteria;
    private final DiscountCalculationStrategy discountCalculationStrategy;

    public DiscountCampaign(String discountId, String name, DiscountCriteria discountCriteria, DiscountCalculationStrategy discountCalculationStrategy) {
        this.discountId = discountId;
        this.name = name;
        this.discountCriteria = discountCriteria;
        this.discountCalculationStrategy = discountCalculationStrategy;
    }

    public boolean isApplicable(Item item) {
        return discountCriteria.isApplicable(item);
    }

    public BigDecimal calculateDiscount(OrderItem item) {
        return discountCalculationStrategy.calculateDiscountedPrice(item.calculatePrice());
    }
}
