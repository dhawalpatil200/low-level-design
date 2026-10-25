package org.example.PaymentGateway.pg.processors;

import org.example.PaymentGateway.pg.entities.PaymentRequest;
import org.example.PaymentGateway.pg.entities.PaymentResponse;
import org.example.PaymentGateway.pg.enums.PaymentStatus;

public class PaypalPaymentProcessor extends AbstractTemplatePaymentProcessor{
    @Override
    protected PaymentResponse doPayment(PaymentRequest paymentRequest) {
        System.out.println("Redirecting to PayPal for payer " + paymentRequest.getPayerId());
        return new PaymentResponse(PaymentStatus.SUCCESS, "Paypal payment successfully completed");
    }
}
