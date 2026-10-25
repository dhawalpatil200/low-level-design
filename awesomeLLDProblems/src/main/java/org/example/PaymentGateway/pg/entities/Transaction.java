package org.example.PaymentGateway.pg.entities;

import org.example.PaymentGateway.pg.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final String id;
    private final PaymentRequest request;
    private PaymentStatus status;
    private final LocalDateTime timestamp;

    public Transaction(PaymentRequest request) {
        this.id = UUID.randomUUID().toString();
        this.request = request;
        this.status = PaymentStatus.INITIATED;
        this.timestamp = LocalDateTime.now();
    }

    public void setStatus(PaymentStatus status) { this.status = status; }
    public String getId() { return id; }
    public PaymentStatus getStatus() { return status; }
    public PaymentRequest getRequest() { return request; }
}