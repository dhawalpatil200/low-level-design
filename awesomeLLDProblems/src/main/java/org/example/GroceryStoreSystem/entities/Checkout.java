package org.example.GroceryStoreSystem.entities;

import org.example.GroceryStoreSystem.discount.DiscountCampaign;

import java.util.List;

public class Checkout {
    private Order currentOrder;
    private final List<DiscountCampaign> activeDiscounts;

    public Checkout(List<DiscountCampaign> activeDiscounts) {
        this.activeDiscounts = activeDiscounts;
        startNewOrder();
    }

    public void startNewOrder() {
        this.currentOrder = new Order();
    }

    public void addItemToOrder(Item item, int quantity) {
        OrderItem orderItem = new OrderItem(item, quantity);
        currentOrder.addItem(orderItem);

        for(DiscountCampaign newDiscount: activeDiscounts) {
            if(newDiscount.isApplicable(item)) {
                if(currentOrder.getApplicableDiscounts().containsKey(orderItem)) {
                    DiscountCampaign existingDiscount = currentOrder.getApplicableDiscounts().get(orderItem);
                    if(orderItem.calculatePriceWithDiscount(newDiscount).compareTo(orderItem.calculatePriceWithDiscount(existingDiscount)) > 0) {
                        currentOrder.applyDiscount(orderItem, newDiscount);
                    }
                } else {
                    currentOrder.applyDiscount(orderItem, newDiscount);
                }
            }
        }
    }
}
