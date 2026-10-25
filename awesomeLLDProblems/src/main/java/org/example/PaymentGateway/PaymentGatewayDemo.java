package org.example.PaymentGateway;

import org.example.PaymentGateway.pg.PaymentGatewayService;
import org.example.PaymentGateway.pg.entities.PaymentRequest;
import org.example.PaymentGateway.pg.enums.PaymentMethod;
import org.example.PaymentGateway.pg.observers.CustomerNotifier;
import org.example.PaymentGateway.pg.observers.MerchantNotifier;

import java.util.Map;

public class PaymentGatewayDemo {
    public static void main(String[] args) {
        PaymentGatewayService paymentGateway = new PaymentGatewayService();

        paymentGateway.addObserver(new MerchantNotifier());
        paymentGateway.addObserver(new CustomerNotifier());

        System.out.println("----------- SCENARIO 1: Successful Credit Card Payment -----------");
        PaymentRequest ccRequest = new PaymentRequest(
                "idem-cc-001", "U-123", "M-123",150.75, "INR",
                PaymentMethod.CREDIT_CARD, Map.of("cardNumber", "1234..."));

        paymentGateway.processPayment(ccRequest);

        System.out.println("\n----------- SCENARIO 2: Successful PayPal Payment -----------");
        PaymentRequest paypalRequest = new PaymentRequest(
                "idem-pp-001", "U-456", "M-124",88.50, "USD",
                PaymentMethod.PAYPAL, Map.of("email", "customer@example.com"));

        paymentGateway.processPayment(paypalRequest);
    }
}
