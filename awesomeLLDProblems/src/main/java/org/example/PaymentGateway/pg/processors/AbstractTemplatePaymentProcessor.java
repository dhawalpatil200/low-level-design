package org.example.PaymentGateway.pg.processors;

import org.example.PaymentGateway.pg.entities.PaymentRequest;
import org.example.PaymentGateway.pg.entities.PaymentResponse;
import org.example.PaymentGateway.pg.enums.PaymentStatus;

public abstract class AbstractTemplatePaymentProcessor implements PaymentProcessor {
    private static final int MAX_RETRIES = 3;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        int attempt = 0;
        PaymentResponse paymentResponse = null;
        do {
            paymentResponse = doPayment(paymentRequest);
            attempt++;
        } while (attempt < MAX_RETRIES && paymentResponse.getStatus() == PaymentStatus.FAILED);
        return paymentResponse;
    }

    protected abstract PaymentResponse doPayment(PaymentRequest paymentRequest);
}
