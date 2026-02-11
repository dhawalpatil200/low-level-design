package org.example.VendingMachine.states;

import org.example.VendingMachine.VendingMachine;

import java.math.BigDecimal;

public abstract class VendingMachineState {

    protected final VendingMachine vendingMachine;

    protected VendingMachineState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public abstract void selectProduct(String rackCode);
    public abstract void insertMoney(BigDecimal amount);
    public abstract void dispense();
    public abstract void cancel();
}
