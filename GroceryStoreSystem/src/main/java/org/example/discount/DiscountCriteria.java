package org.example.discount;

import org.example.entities.Item;

public interface DiscountCriteria {
    public boolean isApplicable(Item item);
}
