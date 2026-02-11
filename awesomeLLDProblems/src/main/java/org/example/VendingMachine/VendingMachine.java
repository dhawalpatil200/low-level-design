package org.example.VendingMachine;

import org.example.VendingMachine.entities.MachineContext;
import org.example.VendingMachine.states.*;

import java.math.BigDecimal;

public class VendingMachine {

    private final InventoryManager inventoryManager;
    private final MachineContext context = new MachineContext();

    private final VendingMachineState idleState;
    private final VendingMachineState hasProductState;
    private final VendingMachineState readyToDispenseState;
    private final VendingMachineState dispensingState;

    private VendingMachineState currentState;

    public VendingMachine(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;

        this.idleState = new IdleState(this);
        this.hasProductState = new HasProductState(this);
        this.readyToDispenseState = new ReadyToDispenseState(this);
        this.dispensingState = new DispensingState(this);

        this.currentState = idleState;
    }

    // Main use cases/ actions
    public synchronized void selectProduct(String rackId) {
        currentState.selectProduct(rackId);
    }

    public synchronized void insertMoney(BigDecimal amount) {
        currentState.insertMoney(amount);
    }

    public synchronized void dispense() {
        currentState.dispense();
    }

    public synchronized void cancel() {
        currentState.cancel();
    }

    // transition methods
    public void setIdleState() {
        currentState = idleState;
    }

    public void setHasProductState() {
        currentState = hasProductState;
    }

    public void setReadyToDispenseState() {
        currentState = readyToDispenseState;
    }

    public void setDispensingState() {
        currentState = dispensingState;
    }

    // helper methods
    public void refund() {
        System.out.println("Refunding: " + context.getAmount());
    }

    public void reset() {
        context.reset();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public MachineContext getContext() {
        return context;
    }
}
