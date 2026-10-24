package org.example.inventoryManagement.Ims.observers;

import org.example.inventoryManagement.Ims.services.LowStockAlertService;
import org.example.inventoryManagement.Ims.entities.StockChange;
import org.example.inventoryManagement.Ims.entities.StockMovement;

public class LowStockObserver implements InventoryObserver{
    private final LowStockAlertService lowStockAlertService;

    public LowStockObserver(LowStockAlertService lowStockAlertService) {
        this.lowStockAlertService = lowStockAlertService;
    }

    @Override
    public void onStockUpdate(StockChange stockChange) {
        lowStockAlertService.checkLowStock(stockChange.productId(), stockChange.warehouseId(), stockChange.currentStock());
    }

    @Override
    public void onStockMovement(StockMovement stockMovement) {
        lowStockAlertService.checkLowStock(stockMovement.productId(), stockMovement.fromWarehouseId(), stockMovement.sourceStockAfter());
    }
}
