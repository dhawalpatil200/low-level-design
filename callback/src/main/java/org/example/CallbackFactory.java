package org.example;

import org.example.callbackStrategy.DecisionCallbackStrategy;
import org.example.callbackStrategy.KycCallbackStrategy;

import org.example.model.DecisionRequest;
import org.example.model.KycRequest;

import java.util.HashMap;
import java.util.Map;

public class CallbackFactory {

    private final Map<String, CallbackRegistration<?>> registry = new HashMap<>();

    public CallbackFactory() {
        register("KYC", new KycCallbackStrategy(), KycRequest.class);
        register("DECISION", new DecisionCallbackStrategy(), DecisionRequest.class);
    }

    private <T> void register(String key, CallbackHandler<T> handler, Class<T> type) {
        registry.put(key, new CallbackRegistration<>(handler, type));
    }

    @SuppressWarnings("unchecked")
    public <T> CallbackRegistration<T> getCallbackInstance(String type) {
        CallbackRegistration<?> registration = registry.get(type);
        if (registration == null) {
            throw new IllegalArgumentException("No callback handler found for type: " + type);
        }
        return (CallbackRegistration<T>) registration;
    }

    public static class CallbackRegistration<T> {
        private final CallbackHandler<T> handler;
        private final Class<T> requestType;

        public CallbackRegistration(CallbackHandler<T> handler, Class<T> requestType) {
            this.handler = handler;
            this.requestType = requestType;
        }

        public CallbackHandler<T> getHandler() {
            return handler;
        }

        public Class<T> getRequestType() {
            return requestType;
        }
    }
}
