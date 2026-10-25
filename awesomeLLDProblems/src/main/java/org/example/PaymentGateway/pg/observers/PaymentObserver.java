package org.example.PaymentGateway.pg.observers;

import org.example.PaymentGateway.pg.entities.Transaction;

public interface PaymentObserver {
    void onTransactionUpdate(Transaction transaction);
}
