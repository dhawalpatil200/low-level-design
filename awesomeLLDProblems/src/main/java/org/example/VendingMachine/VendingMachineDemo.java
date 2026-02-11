package org.example.VendingMachine;

import org.example.VendingMachine.entities.Product;
import org.example.VendingMachine.entities.Rack;

import java.math.BigDecimal;

public class VendingMachineDemo {
    public static void main(String[] args) {
        // Update inventory
        InventoryManager inventoryManager = getInventoryManager();

        // create vending machine
        VendingMachine vendingMachine = new VendingMachine(inventoryManager);

        vendingMachine.selectProduct("101");
        vendingMachine.insertMoney(BigDecimal.valueOf(4L));
        vendingMachine.dispense();
        vendingMachine.insertMoney(BigDecimal.valueOf(4L));
        vendingMachine.dispense();

        vendingMachine.selectProduct("103");
        vendingMachine.insertMoney(BigDecimal.valueOf(8L));
        vendingMachine.dispense();

//        vendingMachine.selectProduct("104");
    }

    private static InventoryManager getInventoryManager() {
        Product product1 = new Product("a", "chips", BigDecimal.valueOf(5L));
        Product product2 = new Product("b", "cookies", BigDecimal.valueOf(10L));
        Product product3 = new Product("c", "drink", BigDecimal.valueOf(8L));

        Rack rack1 = new Rack("101", product1, 3);
        Rack rack2 = new Rack("102", product2, 1);
        Rack rack3 = new Rack("103", product3, 0);

        // update inventory
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.addRack(rack1);
        inventoryManager.addRack(rack2);
        inventoryManager.addRack(rack3);
        return inventoryManager;
    }
}