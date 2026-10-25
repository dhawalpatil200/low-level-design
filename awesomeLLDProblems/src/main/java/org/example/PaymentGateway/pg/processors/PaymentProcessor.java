package org.example.PaymentGateway.pg.processors;

import org.example.PaymentGateway.pg.entities.PaymentRequest;
import org.example.PaymentGateway.pg.entities.PaymentResponse;

public interface PaymentProcessor {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
