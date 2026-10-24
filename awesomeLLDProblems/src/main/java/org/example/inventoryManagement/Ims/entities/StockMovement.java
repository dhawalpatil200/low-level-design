package org.example.inventoryManagement.Ims.entities;

public record StockMovement(
        String productId,
        String fromWarehouseId,
        String toWarehouseId,
        int quantity,
        int sourceStockBefore,
        int sourceStockAfter,
        int destinationStockBefore,
        int destinationStockAfter
) {
}