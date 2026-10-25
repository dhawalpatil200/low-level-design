package org.example.PaymentGateway.pg.processors;

import org.example.PaymentGateway.pg.entities.PaymentRequest;
import org.example.PaymentGateway.pg.entities.PaymentResponse;
import org.example.PaymentGateway.pg.enums.PaymentStatus;

public class UPIPaymentProcessor extends AbstractTemplatePaymentProcessor{
    @Override
    protected PaymentResponse doPayment(PaymentRequest paymentRequest) {
        System.out.println("Processing UPI payment of " + paymentRequest.getAmount() + " " + paymentRequest.getCurrency());
        return new PaymentResponse(PaymentStatus.SUCCESS, "UPI payment successfully completed");
    }
}
