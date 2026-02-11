package org.example.GroceryStoreSystem.entities;

import java.util.HashMap;
import java.util.Map;

public class Catalog {
    private final Map<String, Item> items = new HashMap<>();

    public void updateItem(Item item) {
        items.put(item.getBarcode(), item);
    }

    public Item getItem(String barcode) {
        return items.get(barcode);
    }

    public void removeItem(String barcode) {
        items.remove(barcode);
    }
}
