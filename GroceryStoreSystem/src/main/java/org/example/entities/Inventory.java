package org.example.entities;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Integer> stock =  new HashMap<>();

    public void addStock(String barcode, int count) {
        stock.put(barcode, stock.getOrDefault(barcode, 0) + count);
    }

    public void reduceStock(String barcode, int count) {
       stock.put(barcode, stock.getOrDefault(barcode, 0) - count);
    }

    public int getStock(String barcode) {
        return stock.getOrDefault(barcode, 0);
    }
}
