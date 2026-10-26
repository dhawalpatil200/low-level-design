package org.example.PaymentGateway.pg;

import org.example.PaymentGateway.pg.entities.PaymentRequest;
import org.example.PaymentGateway.pg.entities.PaymentResponse;
import org.example.PaymentGateway.pg.entities.Transaction;
import org.example.PaymentGateway.pg.enums.PaymentStatus;
import org.example.PaymentGateway.pg.observers.PaymentObserver;
import org.example.PaymentGateway.pg.processors.PaymentProcessor;
import org.example.PaymentGateway.pg.processors.PaymentProcessorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentGatewayService {
    private final List<PaymentObserver> observers = new ArrayList<>();
    private final Map<String, Transaction> processed = new ConcurrentHashMap<>();


    public Transaction processPayment(PaymentRequest paymentRequest) {
        String key = paymentRequest.getIdempotencyKey();
        Transaction transaction = new Transaction(paymentRequest);
        Transaction existing = processed.put(key, transaction);
        if(existing != null) {
            return existing;
        }
        try {
            PaymentProcessor paymentProcessor = PaymentProcessorFactory.getPaymentProcessor(paymentRequest.getPaymentMethod());
            PaymentResponse paymentResponse = paymentProcessor.processPayment(paymentRequest);
            updateStatus(transaction, paymentResponse.getStatus());
        } catch (Exception e) {
            System.err.println("Payment processing failed: " + e.getMessage());
            updateStatus(transaction, PaymentStatus.FAILED);
        }
        return transaction;
    }



    // observer method
    public void addObserver(PaymentObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    public void removeObserver(PaymentObserver observer) {
        observers.remove(observer);
    }

    private void updateStatus(Transaction transaction, PaymentStatus paymentStatus) {
        boolean isSuccess = transaction.transitionTo(paymentStatus, PaymentStatus.INITIATED);
        if(isSuccess) {
            notifyAllObservers(transaction);
        }
    }

    private void notifyAllObservers(Transaction transaction) {
        observers.forEach(o -> o.onTransactionUpdate(transaction));
    }
}
