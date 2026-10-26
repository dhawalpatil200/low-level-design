package org.example.ATMSystem.state;

import org.example.ATMSystem.ATM;
import org.example.ATMSystem.entities.Account;
import org.example.ATMSystem.entities.Card;
import org.example.ATMSystem.enums.ATMState;
import org.example.ATMSystem.exceptions.ATMException;

public class CardInsertedState implements ATMStateHandler{
    @Override
    public void insertCard(ATM atm, Card card) {
        throw new ATMException("A card is already inserted");
    }

    @Override
    public void authenticate(ATM atm, String pin) {
        Account account = atm.getBankService().authenticate(
                atm.getCurrentCard().getCardNumber(), pin
        );
        atm.setCurrentAccount(account);
        atm.setState(ATMState.AUTHENTICATED);
        System.out.println("Authentication successful for card " +
                atm.getCurrentCard().getCardNumber());
    }

    @Override
    public void withdraw(ATM atm, double amount) {
        throw new ATMException("Please authenticate first");
    }

    @Override
    public void deposit(ATM atm, double amount) {
        throw new ATMException("Please authenticate first");
    }

    @Override
    public double checkBalance(ATM atm) {
        throw new ATMException("Please authenticate first");
    }


    @Override
    public void ejectCard(ATM atm) {
        atm.setCurrentCard(null);
        atm.setState(ATMState.IDLE);
        System.out.println("Card ejected successfully");
    }
}
