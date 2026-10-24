package org.example.inventoryManagement.Ims.entities;

public record StockChange(
        TransactionType transactionType,
        String warehouseId,
        String productId,
        int quantityChanged,
        int previousStock,
        int currentStock
) {
}