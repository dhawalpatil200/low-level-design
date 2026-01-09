package org.example;

import org.example.model.DecisionRequest;
import org.example.model.KycRequest;

public class CallbackDelegate {
    private final CallbackFactory callbackFactory;

    public CallbackDelegate(CallbackFactory callbackFactory) {
        this.callbackFactory = callbackFactory;
    }

    public void kycCallback(KycRequest kycRequest) {
        CallbackFactory.CallbackRegistration<KycRequest> registration = callbackFactory.getCallbackInstance("KYC");
        int code = registration.getHandler().processCallback(kycRequest);
    }

    public void decisionCallback(DecisionRequest decisionRequest) {
        CallbackFactory.CallbackRegistration<DecisionRequest> registration = callbackFactory.getCallbackInstance("DECISION");
        int code = registration.getHandler().processCallback(decisionRequest);
    }
}
