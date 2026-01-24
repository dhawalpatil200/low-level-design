package org.example.states;

import org.example.VendingMachine;

import java.math.BigDecimal;

public class DispensingState extends VendingMachineState {

    public DispensingState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectProduct(String rackCode) {
        System.out.println("Dispensing in progress");
    }

    @Override
    public void insertMoney(BigDecimal amount) {
        System.out.println("Dispensing in progress");
    }

    @Override
    public void dispense() {
        var context = vendingMachine.getContext();

        vendingMachine.getInventoryManager().dispenseProduct(context.getRack());
        BigDecimal balance = context.getAmount();
        BigDecimal price = context.getProduct().getUnitPrice();
        BigDecimal change = balance.subtract(price);

        if (change.signum() > 0) {
            System.out.println("Returning change: " + change);
        }

        vendingMachine.reset();
        vendingMachine.setIdleState();

        System.out.println("Product dispensed successfully");
    }

    @Override
    public void cancel() {
        System.out.println("Cannot cancel during dispensing");
    }
}
