package org.example.ATMSystem.entities;

import org.example.ATMSystem.enums.TransactionType;

public class Transaction {
    private final String id;
    private final TransactionType type;
    private final double amount;
    private final String accountNumber;
    private final String timestamp;

    public Transaction(String id, TransactionType type, double amount, String accountNumber) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{id='" + id + "', type=" + type +
                ", amount=$" + amount + ", account='" + accountNumber +
                "', time=" + timestamp + "}";
    }
}
