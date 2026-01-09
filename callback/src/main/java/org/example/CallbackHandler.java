package org.example;

public interface CallbackHandler<T> {
    int processCallback(T request);
}
