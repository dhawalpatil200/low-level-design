package org.example.inventoryManagement.Ims.entities;

import org.example.inventoryManagement.Ims.exceptions.ImsException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private final String warehouseId;
    private final Map<String, Integer> inventory = new ConcurrentHashMap<>();

    public Warehouse(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public synchronized void addStock(String productId, int quantity) {
        assertQuantity(quantity);
        inventory.put(productId, inventory.getOrDefault(productId, 0) + quantity);
    }

    public synchronized void removeStock(String productId, int quantity) {
        assertProductId(productId);
        assertQuantity(quantity);

        int currentQty = inventory.get(productId);
        if(quantity > currentQty) {
            throw  new ImsException("Not enough stock");
        }
        inventory.put(productId, currentQty - quantity);
    }

    public synchronized int getStockCount(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    private void assertProductId(String productId) {
        if(!inventory.containsKey(productId)) {
            throw new ImsException("Product doesn't existing in this inventory");
        }
    }

    private void assertQuantity(int quantity) {
        if(quantity <= 0) {
            throw new ImsException("Invalid quantity");
        }
    }
}
