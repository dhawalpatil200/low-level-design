package org.example.inventoryManagement.Ims;
import org.example.inventoryManagement.Ims.entities.*;
import org.example.inventoryManagement.Ims.exceptions.ImsException;
import org.example.inventoryManagement.Ims.observers.InventoryObserver;
import org.example.inventoryManagement.Ims.services.LowStockAlertService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ims {
    private final Map<String, Warehouse> warehouseMap = new ConcurrentHashMap<>();
    private final Map<String, Product> productMap = new ConcurrentHashMap<>();
    private final List<InventoryObserver> observers;
    private final LowStockAlertService alertService;


    public Ims(List<InventoryObserver> observers, LowStockAlertService alertService) {
        this.observers = observers;
        this.alertService = alertService;
    }

    public void addWarehouse(Warehouse warehouse) {
        if(warehouseMap.containsKey(warehouse.getWarehouseId())) {
            throw new ImsException("Warehouse already exist");
        }
        warehouseMap.put(warehouse.getWarehouseId(), warehouse);
    }

    public void registerProduct(Product product) {
        if(productMap.containsKey(product.getId())) {
            throw new ImsException("Product already exists");
        }
        productMap.put(product.getId(), product);
    }

    public void addProduct(String productId, String warehouseId, int initialQty) {
        assertValidProduct(productId);
        assertValidWarehouse(warehouseId);
        if(initialQty < 0) throw new ImsException("Invalid initial quantity");
        warehouseMap.get(warehouseId).addStock(productId, initialQty);
    }

    public void addStockInWarehouse(String productId, String warehouseId, int quantity) {
        assertValidWarehouse(warehouseId);
        assertValidProduct(productId);
        Warehouse warehouse = warehouseMap.get(warehouseId);
        int prevStock = warehouse.getStockCount(productId);
        warehouse.addStock(productId, quantity);
        notifyStockUpdate(new StockChange(
                TransactionType.ADD,
                warehouseId,
                productId,
                quantity,
                prevStock,
                warehouse.getStockCount(productId)
            )
        );
    }


    public void removeStockFromWarehouse(String productId, String warehouseId, int quantity) {
        assertValidWarehouse(warehouseId);
        assertValidProduct(productId);
        assertValidWarehouse(warehouseId);
        assertValidProduct(productId);
        Warehouse warehouse = warehouseMap.get(warehouseId);
        int prevStock = warehouse.getStockCount(productId);
        warehouse.removeStock(productId, quantity);
        notifyStockUpdate(new StockChange(
                TransactionType.REMOVE,
                warehouseId,
                productId,
                quantity,
                prevStock,
                warehouse.getStockCount(productId)
            )
        );
    }

    public synchronized void moveStockAcrossWarehouse(String productId, String fromWarehouseId, String toWarehouseId, int quantity) {
        assertValidProduct(productId);
        assertValidWarehouse(fromWarehouseId);
        assertValidWarehouse(toWarehouseId);

        Warehouse fromWarehouse = warehouseMap.get(fromWarehouseId);
        int sourceStockBefore = fromWarehouse.getStockCount(productId);
        Warehouse toWarehouse = warehouseMap.get(toWarehouseId);
        int destStockBefore = toWarehouse.getStockCount(productId);

        fromWarehouse.removeStock(productId, quantity);
        toWarehouse.addStock(productId, quantity);
        notifyStockMovement(new StockMovement(
                productId,
                fromWarehouseId,
                toWarehouseId,
                quantity,
                sourceStockBefore,
                fromWarehouse.getStockCount(productId),
                destStockBefore,
                toWarehouse.getStockCount(productId)
        ));
    }

    public synchronized int getStockCount(String warehouseId, String productId) {
        assertValidWarehouse(warehouseId);
        assertValidProduct(productId);
        return warehouseMap.get(warehouseId).getStockCount(productId);
    }

    public void addStockAlert(String productId, String warehouseId, int threshold) {
        assertValidProduct(productId);
        assertValidWarehouse(warehouseId);
        alertService.addStockAlert(productId, warehouseId, threshold);
    }


    private void assertValidProduct(String productId) {
        if(!productMap.containsKey(productId)) throw new ImsException("Product not registered in system");
    }
    private void assertValidWarehouse(String warehouseId) {
        if(!warehouseMap.containsKey(warehouseId)) throw new ImsException("Warehouse not registered in system");
    }

    private void notifyStockUpdate(StockChange stockChange) {
        for (InventoryObserver observer : observers) {
            observer.onStockUpdate(stockChange);
        }
    }
    private void notifyStockMovement(StockMovement stockMovement) {
        for (InventoryObserver observer : observers) {
            observer.onStockMovement(stockMovement
            );
        }
    }
}
