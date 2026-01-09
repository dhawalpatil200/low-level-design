package org.example.entities;

import org.example.discount.DiscountCampaign;

import java.math.BigDecimal;
import java.util.*;

public class Order {
    private final String orderId;
    private final List<OrderItem> items = new ArrayList<>();
    private final Map<OrderItem, DiscountCampaign> applicableDiscounts = new HashMap<>();
    private BigDecimal paymentAmount = BigDecimal.ZERO;

    public Order() {
        this.orderId = String.valueOf(UUID.randomUUID());
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public BigDecimal calculateSubtotal() {
        return items.stream()
                .map(OrderItem::calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotal() {
        return items.stream()
                .map(item -> {
                    DiscountCampaign discountCampaign = applicableDiscounts.get(item);
                    return discountCampaign != null ? item.calculatePriceWithDiscount(discountCampaign) : item.calculatePrice();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void applyDiscount(OrderItem item, DiscountCampaign discount) {
        applicableDiscounts.put(item, discount);
    }

    public BigDecimal calculateChange() {
        return paymentAmount.subtract(calculateTotal());
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Map<OrderItem, DiscountCampaign> getApplicableDiscounts() {
        return applicableDiscounts;
    }
}
