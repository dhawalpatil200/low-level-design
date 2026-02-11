package org.example.VendingMachine.states;

import org.example.VendingMachine.InventoryManager;
import org.example.VendingMachine.VendingMachine;
import org.example.VendingMachine.entities.Rack;

import java.math.BigDecimal;

public class IdleState extends VendingMachineState {

    public IdleState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectProduct(String rackCode) {
        InventoryManager inventory = vendingMachine.getInventoryManager();
        Rack rack = inventory.getRack(rackCode);

        if (rack == null) {
            throw new IllegalArgumentException("Invalid rack code");
        }

        if (rack.getCount() == 0) {
            System.out.println("Product out of stock");
            return;
        }

        vendingMachine.getContext().setRack(rack);
        vendingMachine.getContext().setProduct(rack.getProduct());

        System.out.println("Product selected: " + rackCode);
        vendingMachine.setHasProductState();
    }

    @Override
    public void insertMoney(BigDecimal amount) {
        System.out.println("Please select a product first");
    }

    @Override
    public void dispense() {
        System.out.println("Please select a product first");
    }

    @Override
    public void cancel() {
        System.out.println("Nothing to cancel");
    }
}
