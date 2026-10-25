package org.example.PaymentGateway.pg.entities;

import org.example.PaymentGateway.pg.enums.PaymentMethod;

import java.util.Map;

public class PaymentRequest {
    private final String idempotencyKey;
    private final String payerId;
    private final String merchantId;
    private final double amount;
    private final String currency;
    private final PaymentMethod paymentMethod;
    private final Map<String, String> paymentDetails;

    public PaymentRequest(String idempotencyKey, String payerId, String merchantId, double amount, String currency, PaymentMethod paymentMethod, Map<String, String> paymentDetails) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("idempotencyKey is required");
        }
        if (payerId == null || payerId.isBlank()) {
            throw new IllegalArgumentException("payerId is required");
        }
        if (merchantId == null || merchantId.isBlank()) {
            throw new IllegalArgumentException("merchantId is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency is required");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("paymentMethod is required");
        }

        this.idempotencyKey = idempotencyKey;
        this.payerId = payerId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
    }

    public String getIdempotencyKey() { return idempotencyKey; }
    public String getPayerId() { return payerId; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public String getMerchantId() {
        return merchantId;
    }
}
