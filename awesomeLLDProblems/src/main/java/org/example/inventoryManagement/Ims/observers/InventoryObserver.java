package org.example.inventoryManagement.Ims.observers;

import org.example.inventoryManagement.Ims.entities.StockChange;
import org.example.inventoryManagement.Ims.entities.StockMovement;

public interface InventoryObserver {
    void onStockUpdate(StockChange stockChange);
    void onStockMovement(StockMovement stockMovement);
}
