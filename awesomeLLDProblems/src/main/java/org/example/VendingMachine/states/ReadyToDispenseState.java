package org.example.VendingMachine.states;


import org.example.VendingMachine.VendingMachine;

import java.math.BigDecimal;

public class ReadyToDispenseState extends VendingMachineState {

    public ReadyToDispenseState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectProduct(String rackCode) {
        System.out.println("Product already selected");
    }

    @Override
    public void insertMoney(BigDecimal amount) {
        System.out.println("Payment already completed");
    }

    @Override
    public void dispense() {
        vendingMachine.setDispensingState();
        vendingMachine.dispense();
    }

    @Override
    public void cancel() {
        vendingMachine.refund();
        vendingMachine.reset();
        vendingMachine.setIdleState();
    }
}
