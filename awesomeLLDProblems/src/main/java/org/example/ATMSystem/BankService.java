package org.example.ATMSystem;

import org.example.ATMSystem.entities.Account;
import org.example.ATMSystem.entities.Card;
import org.example.ATMSystem.entities.Transaction;
import org.example.ATMSystem.enums.TransactionType;
import org.example.ATMSystem.exceptions.ATMException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BankService {
    private final Map<String, Account> accounts;
    private final Map<String, Card> cards;
    private final Map<String, List<Transaction>> transactionHistory;
    private int transactionCounter = 0;

    public BankService() {
        this.accounts = new ConcurrentHashMap<>();
        this.cards = new ConcurrentHashMap<>();
        this.transactionHistory = new ConcurrentHashMap<>();
    }

    public void createAccount(String accountNumber, double initialBalance) {
        accounts.put(accountNumber, new Account(accountNumber, initialBalance));
    }

    public void createCard(String cardNumber, String pin, String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new ATMException("Account " + accountNumber + " does not exist");
        }
        cards.put(cardNumber, new Card(cardNumber, pin, accountNumber));
    }

    public Account authenticate(String cardNumber, String pin) {
        Card card = cards.get(cardNumber);
        if (card == null) {
            throw new ATMException("Card not recognized");
        }
        if (!card.getPin().equals(pin)) {
            throw new ATMException("Incorrect PIN");
        }
        Account account = accounts.get(card.getAccountNumber());
        if (account == null) {
            throw new ATMException("Account not found for card " + cardNumber);
        }
        return account;
    }

    public double getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new ATMException("Account " + accountNumber + " not found");
        }
        return account.getBalance();
    }

    public void debit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new ATMException("Account " + accountNumber + " not found");
        }
        account.debit(amount);
    }

    public void credit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new ATMException("Account " + accountNumber + " not found");
        }
        account.credit(amount);
    }

    public synchronized Transaction recordTransaction(TransactionType type, double amount,
                                                      String accountNumber) {
        String id = "TXN" + (++transactionCounter);
        Transaction transaction = new Transaction(id, type, amount, accountNumber);
        transactionHistory.computeIfAbsent(accountNumber, k -> new ArrayList<>()).add(transaction);
        return transaction;
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionHistory.getOrDefault(accountNumber, new ArrayList<>());
    }
}