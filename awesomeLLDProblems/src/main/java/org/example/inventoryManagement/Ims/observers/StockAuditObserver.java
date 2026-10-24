package org.example.inventoryManagement.Ims.observers;

import org.example.inventoryManagement.Ims.entities.StockChange;
import org.example.inventoryManagement.Ims.entities.StockMovement;

import java.time.Instant;

public class StockAuditObserver implements InventoryObserver{
    @Override
    public void onStockUpdate(StockChange stockChange) {
        System.out.printf("[AUDIT][%s][%s][warehouseId=%s][productId=%s][quantityChanged=%d]%n", Instant.now(), stockChange.transactionType(), stockChange.warehouseId(), stockChange.productId(), stockChange.quantityChanged());
    }

    @Override
    public void onStockMovement(StockMovement stockMovement) {
        System.out.printf("[AUDIT][%s][TRANSFER][warehouseFromId=%s][warehouseToId=%s][productId=%s][quantity=%d]%n", Instant.now(), stockMovement.fromWarehouseId(), stockMovement.toWarehouseId(), stockMovement.productId(), stockMovement.quantity());
    }
}
