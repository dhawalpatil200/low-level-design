package org.example.ATMSystem.entities;

import org.example.ATMSystem.exceptions.ATMException;

public class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public synchronized void debit(double amount) {
        if (amount > balance) {
            throw new ATMException(
                    "Insufficient funds. Account balance: $" + balance + ", requested: $" + amount
            );
        }
        balance -= amount;
    }

    public synchronized void credit(double amount) {
        balance += amount;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
