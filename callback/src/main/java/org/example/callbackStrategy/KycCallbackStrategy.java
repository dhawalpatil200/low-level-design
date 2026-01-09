package org.example.callbackStrategy;

import org.example.CallbackHandler;
import org.example.model.KycRequest;

public class KycCallbackStrategy implements CallbackHandler<KycRequest> {
    @Override
    public int processCallback(KycRequest kycRequest) {
        System.out.println("Processing kyc callback");
        return 0;
    }
}
