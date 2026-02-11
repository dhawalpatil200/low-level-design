package org.example.GroceryStoreSystem.discount;


import org.example.GroceryStoreSystem.entities.Item;

public interface DiscountCriteria {
    public boolean isApplicable(Item item);
}
