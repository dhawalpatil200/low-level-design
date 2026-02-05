package org.example.states.light;

import org.example.TrafficLight;

public interface SignalState {
    void handle(TrafficLight context);
}
