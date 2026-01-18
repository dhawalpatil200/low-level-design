package org.example.entities;

import java.math.BigDecimal;

public class MachineContext {

    private Product product;
    private Rack rack;
    private BigDecimal amount = BigDecimal.ZERO;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void reset() {
        product = null;
        rack = null;
        amount = BigDecimal.ZERO;
    }
}
