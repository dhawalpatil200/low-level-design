package org.example.inventoryManagement.Ims.services;

import org.example.inventoryManagement.Ims.exceptions.ImsException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LowStockAlertService {
    private final Map<String, Integer> alertMap = new ConcurrentHashMap<>();

    public void checkLowStock(String productId, String warehouseId, int quantity) {
        if(quantity < 0) throw new ImsException("Invalid quantity");
        int threshold = alertMap.getOrDefault(getKey(productId, warehouseId), 0);

        if(quantity < threshold) {
            System.out.printf("[LOW STOCK ALERT][%s][%s][%d]\n", warehouseId, productId, quantity);
        }
    }

    public void addStockAlert(String productId, String warehouseId, int threshold) {
        if(threshold < 0) throw new ImsException("Invalid threshold");
        alertMap.put(getKey(productId, warehouseId), threshold);
    }

    private String getKey(String productId, String warehouseId) {
        return productId + "-" + warehouseId;
    }
}
