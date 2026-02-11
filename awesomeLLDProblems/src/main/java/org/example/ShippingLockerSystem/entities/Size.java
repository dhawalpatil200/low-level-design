package org.example.ShippingLockerSystem.entities;

import java.math.BigDecimal;

public enum Size {
    SMALL("small", new BigDecimal("10.0"), new BigDecimal("10.0"), new BigDecimal("10.0")),
    MEDIUM("medium", new BigDecimal("15.0"), new BigDecimal("15.0"), new BigDecimal("15.0")),
    LARGE("large", new BigDecimal("20.0"), new BigDecimal("20.0"), new BigDecimal("20.0"));
    private String name;
    private final BigDecimal height, width, depth;

    Size(String small, BigDecimal height, BigDecimal width, BigDecimal depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }


    public BigDecimal getHeight() {
        return height;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getDepth() {
        return depth;
    }
}
