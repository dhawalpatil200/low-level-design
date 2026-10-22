package org.example.ATMSystem.state;

import org.example.ATMSystem.ATM;
import org.example.ATMSystem.entities.Card;

public interface ATMStateHandler {
    void insertCard(ATM atm, Card card);
    void authenticate(ATM atm, String pin);
    void withdraw(ATM atm, double amount);
    void deposit(ATM atm, double amount);
    double checkBalance(ATM atm);
    void ejectCard(ATM atm);
}
