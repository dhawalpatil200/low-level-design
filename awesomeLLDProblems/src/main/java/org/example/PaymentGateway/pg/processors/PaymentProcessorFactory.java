package org.example.PaymentGateway.pg.processors;

import org.example.PaymentGateway.pg.enums.PaymentMethod;

public class PaymentProcessorFactory {
    public static PaymentProcessor getPaymentProcessor(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case UPI -> new UPIPaymentProcessor();
            case PAYPAL -> new PaypalPaymentProcessor();
            case CREDIT_CARD -> new CreditCardPaymentProcessor();
            default -> throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod.name());
        };
    }
}
