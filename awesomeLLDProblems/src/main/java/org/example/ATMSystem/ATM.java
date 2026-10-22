package org.example.ATMSystem;

import org.example.ATMSystem.entities.Account;
import org.example.ATMSystem.entities.Card;
import org.example.ATMSystem.enums.ATMState;
import org.example.ATMSystem.enums.Denomination;
import org.example.ATMSystem.enums.TransactionType;
import org.example.ATMSystem.exceptions.ATMException;
import org.example.ATMSystem.state.ATMStateHandler;
import org.example.ATMSystem.state.AuthenticatedState;
import org.example.ATMSystem.state.CardInsertedState;
import org.example.ATMSystem.state.IdleState;

import java.util.HashMap;
import java.util.Map;

public class ATM {
    private static volatile ATM instance;
    private static final Object lock = new Object();

    private ATMState currentState;
    private final Map<ATMState, ATMStateHandler> stateHandlers;
    private final BankService bankService;
    private final CashDispenser cashDispenser;
    private Card currentCard;
    private Account currentAccount;


    public static ATM getInstance(BankService bankService, CashDispenser cashDispenser) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ATM(bankService, cashDispenser);
                }
            }
        }
        return instance;
    }

    public ATM(BankService bankService, CashDispenser cashDispenser) {
        this.bankService = bankService;
        this.cashDispenser = cashDispenser;
        this.currentState = ATMState.IDLE;

        // Initialize state handlers
        this.stateHandlers = new HashMap<>();
        stateHandlers.put(ATMState.IDLE, new IdleState());
        stateHandlers.put(ATMState.CARD_INSERTED, new CardInsertedState());
        stateHandlers.put(ATMState.AUTHENTICATED, new AuthenticatedState());
    }

    // Public methods - all delegate to current state handler
    public synchronized void insertCard(Card card) {
        stateHandlers.get(currentState).insertCard(this, card);
    }

    public synchronized void authenticate(String pin) {
        stateHandlers.get(currentState).authenticate(this, pin);
    }

    public synchronized void withdraw(double amount) {
        stateHandlers.get(currentState).withdraw(this, amount);
    }

    public synchronized void deposit(double amount) {
        stateHandlers.get(currentState).deposit(this, amount);
    }

    public synchronized double checkBalance() {
        return stateHandlers.get(currentState).checkBalance(this);
    }

    public synchronized void ejectCard() {
        stateHandlers.get(currentState).ejectCard(this);
    }

    public void performWithdrawal(double amount) {
        int intAmount = (int) amount;
        if (intAmount <= 0 || intAmount % 10 != 0) {
            throw new ATMException("Amount must be a positive multiple of $10");
        }

        String accountNumber = currentAccount.getAccountNumber();
        double balance = bankService.getBalance(accountNumber);
        if (amount > balance) {
            throw new ATMException(
                    "Insufficient funds. Account balance: $" + balance + ", requested: $" + amount
            );
        }

        if (!cashDispenser.canDispense(intAmount)) {
            throw new ATMException("ATM cannot dispense $" + intAmount +
                    " with available denominations");
        }

        // CRITICAL: Dispense cash BEFORE debiting account
        // If the dispenser jams after debiting, the customer loses money
        Map<Denomination, Integer> dispensed = cashDispenser.dispense(intAmount);
        bankService.debit(accountNumber, amount);

        System.out.println("Dispensing $" + intAmount + ": " + dispensed);
        bankService.recordTransaction(TransactionType.WITHDRAWAL, amount, accountNumber);
    }

    public void performDeposit(double amount) {
        // accepting cash and validating is out of scope here : just accept any positive value
        if (amount <= 0) {
            throw new ATMException("Deposit amount must be positive");
        }
        String accountNumber = currentAccount.getAccountNumber();
        bankService.credit(accountNumber, amount);
        System.out.println("Deposited $" + amount + " successfully");
        bankService.recordTransaction(TransactionType.DEPOSIT, amount, accountNumber);
    }

    public double performBalanceInquiry() {
        String accountNumber = currentAccount.getAccountNumber();
        double balance = bankService.getBalance(accountNumber);
        bankService.recordTransaction(TransactionType.BALANCE_INQUIRY, 0, accountNumber);
        return balance;
    }


    // Internal methods for state handlers to call
    public void setState(ATMState state) {
        this.currentState = state;
    }

    public void setCurrentCard(Card card) {
        this.currentCard = card;
    }

    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public BankService getBankService() {
        return bankService;
    }

    public ATMState getCurrentState() {
        return currentState;
    }
}
