package org.example.PaymentGateway.pg.observers;

import org.example.PaymentGateway.pg.entities.Transaction;
import org.example.PaymentGateway.pg.enums.PaymentStatus;

public class CustomerNotifier implements PaymentObserver{
    @Override
    public void onTransactionUpdate(Transaction transaction) {
        if (transaction.getStatus() == PaymentStatus.SUCCESS) {
            System.out.println("--- CUSTOMER EMAIL ---");
            System.out.println("Your payment of " + transaction.getRequest().getAmount() + " was successful. Transaction ID: " + transaction.getId());
            System.out.println("----------------------");
        }
    }
}
