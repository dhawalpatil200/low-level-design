package org.example.callbackStrategy;

import org.example.CallbackHandler;
import org.example.model.DecisionRequest;

public class DecisionCallbackStrategy implements CallbackHandler<DecisionRequest> {

    @Override
    public int processCallback(DecisionRequest decisionRequest) {
        System.out.println("Processing decision callback");
        return 0;
    }
}
