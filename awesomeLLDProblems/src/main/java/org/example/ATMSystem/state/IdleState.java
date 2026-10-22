package org.example.ATMSystem.state;

import org.example.ATMSystem.ATM;
import org.example.ATMSystem.entities.Card;
import org.example.ATMSystem.enums.ATMState;
import org.example.ATMSystem.exceptions.ATMException;

public class IdleState implements ATMStateHandler {
    @Override
    public void insertCard(ATM atm, Card card) {
        atm.setCurrentCard(card);
        atm.setState(ATMState.CARD_INSERTED);
        System.out.println("Card " + card.getCardNumber() + " inserted successfully");
    }

    @Override
    public void authenticate(ATM atm, String pin) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public void withdraw(ATM atm, double amount) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public void deposit(ATM atm, double amount) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public double checkBalance(ATM atm) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public void ejectCard(ATM atm) {
        throw new ATMException("No card to eject");
    }
}