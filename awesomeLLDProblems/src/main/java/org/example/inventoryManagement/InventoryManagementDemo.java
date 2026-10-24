package org.example.inventoryManagement;

import org.example.inventoryManagement.Ims.*;
import org.example.inventoryManagement.Ims.entities.Product;
import org.example.inventoryManagement.Ims.entities.Warehouse;
import org.example.inventoryManagement.Ims.observers.InventoryObserver;
import org.example.inventoryManagement.Ims.observers.LowStockObserver;
import org.example.inventoryManagement.Ims.observers.StockAuditObserver;
import org.example.inventoryManagement.Ims.services.LowStockAlertService;

import java.util.List;

public class InventoryManagementDemo {

    public static void main(String[] args) {

        // 1. Create products
        Product apple = new Product("p-id-1", "Apple");
        Product banana = new Product("p-id-2", "Banana");

        // 2. Create warehouses
        Warehouse warehouse1 = new Warehouse("w-id-1");
        Warehouse warehouse2 = new Warehouse("w-id-2");

        // 3. Create alert service
        LowStockAlertService alertService =
                new LowStockAlertService();

        // 4. Register observers
        List<InventoryObserver> observers = List.of(
                new StockAuditObserver(),
                new LowStockObserver(alertService)
        );

        // 5. Initialize IMS facade
        Ims ims = new Ims(observers, alertService);

        // 6. Register warehouses and products
        ims.addWarehouse(warehouse1);
        ims.addWarehouse(warehouse2);

        ims.registerProduct(apple);
        ims.registerProduct(banana);

        // 7. Initialize stock in each warehouse
        ims.addProduct(apple.getId(), "w-id-1", 20);
        ims.addProduct(apple.getId(), "w-id-2", 5);

        ims.addProduct(banana.getId(), "w-id-1", 15);
        ims.addProduct(banana.getId(), "w-id-2", 10);

        // 8. Configure low-stock thresholds
        ims.addStockAlert(apple.getId(), "w-id-1", 10);
        ims.addStockAlert(apple.getId(), "w-id-2", 8);

        ims.addStockAlert(banana.getId(), "w-id-1", 5);
        ims.addStockAlert(banana.getId(), "w-id-2", 5);

        // 9. Display initial inventory
        System.out.println("\n=== Initial Inventory ===");
        printStock(ims, apple, "w-id-1");
        printStock(ims, apple, "w-id-2");
        printStock(ims, banana, "w-id-1");
        printStock(ims, banana, "w-id-2");

        // 10. Add stock
        System.out.println("\n=== Add Stock ===");
        ims.addStockInWarehouse(apple.getId(), "w-id-1", 5);
        printStock(ims, apple, "w-id-1");

        // 11. Remove stock: Apple in W1 goes from 25 to 8
        System.out.println("\n=== Remove Stock ===");
        ims.removeStockFromWarehouse(
                apple.getId(), "w-id-1", 17);
        printStock(ims, apple, "w-id-1");

        // 12. Remove more stock while already below threshold
        System.out.println("\n=== Remove More Stock ===");
        ims.removeStockFromWarehouse(
                apple.getId(), "w-id-1", 2);
        printStock(ims, apple, "w-id-1");

        // 13. Replenish stock
        System.out.println("\n=== Replenish Stock ===");
        ims.addStockInWarehouse(apple.getId(), "w-id-1", 20);
        printStock(ims, apple, "w-id-1");

        // 14. Transfer stock between warehouses
        System.out.println("\n=== Transfer Stock ===");
        ims.moveStockAcrossWarehouse(
                apple.getId(),
                "w-id-1",
                "w-id-2",
                18
        );

        printStock(ims, apple, "w-id-1");
        printStock(ims, apple, "w-id-2");

        // 15. Check inventory for another product
        System.out.println("\n=== Banana Inventory ===");
        printStock(ims, banana, "w-id-1");
        printStock(ims, banana, "w-id-2");
    }

    private static void printStock(
            Ims ims, Product product, String warehouseId) {

        int quantity = ims.getStockCount(
                warehouseId, product.getId());

        System.out.printf(
                "Product: %s | Warehouse: %s | Stock: %d%n",
                product.getId(),
                warehouseId,
                quantity
        );
    }
}
