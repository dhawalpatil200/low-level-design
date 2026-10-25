package org.example.PaymentGateway.pg.processors;

import org.example.PaymentGateway.pg.entities.PaymentRequest;
import org.example.PaymentGateway.pg.entities.PaymentResponse;
import org.example.PaymentGateway.pg.enums.PaymentStatus;

public class CreditCardPaymentProcessor extends AbstractTemplatePaymentProcessor {
    @Override
    protected PaymentResponse doPayment(PaymentRequest paymentRequest) {
        System.out.println("Processing credit card payment of amount " + paymentRequest.getAmount() + " " + paymentRequest.getCurrency());
        return new PaymentResponse(PaymentStatus.SUCCESS, "Credit card payment successfully completed");
    }
}
