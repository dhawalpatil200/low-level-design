package org.example.ATMSystem;

import org.example.ATMSystem.entities.Card;
import org.example.ATMSystem.entities.Transaction;
import org.example.ATMSystem.enums.Denomination;
import org.example.ATMSystem.exceptions.ATMException;

public class ATMDemo {
    public static void main(String[] args) {
        // Setup bank service with test accounts
        BankService bankService = new BankService();
        bankService.createAccount("ACC001", 5000.00);
        bankService.createAccount("ACC002", 1000.00);
        bankService.createCard("CARD001", "1234", "ACC001");
        bankService.createCard("CARD002", "5678", "ACC002");

        // Setup cash dispenser with initial inventory
        CashDispenser cashDispenser = new CashDispenser();
        cashDispenser.addCash(Denomination.HUNDRED, 10);
        cashDispenser.addCash(Denomination.FIFTY, 20);
        cashDispenser.addCash(Denomination.TWENTY, 30);
        cashDispenser.addCash(Denomination.TEN, 50);

        ATM atm = ATM.getInstance(bankService, cashDispenser);

        // Scenario 1: Successful withdrawal
        System.out.println("========== SCENARIO 1: Withdrawal ==========");
        Card card1 = new Card("CARD001", "1234", "ACC001");
        atm.insertCard(card1);
        atm.authenticate("1234");
        System.out.println("Balance: $" + atm.checkBalance());
        atm.withdraw(170);
        System.out.println("Balance after withdrawal: $" + atm.checkBalance());
        atm.ejectCard();

        // Scenario 2: Deposit
        System.out.println("\n========== SCENARIO 2: Deposit ==========");
        Card card2 = new Card("CARD002", "5678", "ACC002");
        atm.insertCard(card2);
        atm.authenticate("5678");
        System.out.println("Balance: $" + atm.checkBalance());
        atm.deposit(500);
        System.out.println("Balance after deposit: $" + atm.checkBalance());
        atm.ejectCard();

        // Scenario 3: Error handling - insufficient funds
        System.out.println("\n========== SCENARIO 3: Error Cases ==========");
        atm.insertCard(card1);
        atm.authenticate("1234");
        try {
            atm.withdraw(100000);
        } catch (ATMException e) {
            System.out.println("Error: " + e.getMessage());
        }
        atm.ejectCard();

        System.out.println("\n========== Transaction History (ACC001) ==========");
        for (Transaction transaction : bankService.getTransactionHistory("ACC001")) {
            System.out.println(transaction.getId() + " | " + transaction.getType()
                    + " | $" + transaction.getAmount());
        }

        System.out.println("\nATM total cash remaining: $" + cashDispenser.getTotalCash());
    }
}
