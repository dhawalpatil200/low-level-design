package org.example.VendingMachine.states;

import org.example.VendingMachine.VendingMachine;

import java.math.BigDecimal;

public class HasProductState extends VendingMachineState {

    public HasProductState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectProduct(String rackCode) {
        System.out.println("Product already selected");
    }

    @Override
    public void insertMoney(BigDecimal amount) {
        vendingMachine.getContext().addAmount(amount);

        BigDecimal balance = vendingMachine.getContext().getAmount();
        BigDecimal price = vendingMachine.getContext().getProduct().getUnitPrice();

        System.out.println("Inserted: " + amount + ", Balance: " + balance);

        if (balance.compareTo(price) >= 0) {
            vendingMachine.setReadyToDispenseState();
        }
    }

    @Override
    public void dispense() {
        System.out.println("Please complete payment first");
    }

    @Override
    public void cancel() {
        vendingMachine.refund();
        vendingMachine.reset();
        vendingMachine.setIdleState();
    }
}
