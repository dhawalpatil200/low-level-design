package org.example.entities;

import java.math.BigDecimal;

public class PackageSize {
    private final BigDecimal height, width, depth;

    public PackageSize(BigDecimal height, BigDecimal width, BigDecimal depth) {
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
