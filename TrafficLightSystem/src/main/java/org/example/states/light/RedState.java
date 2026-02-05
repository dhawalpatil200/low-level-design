package org.example.states.light;

import org.example.TrafficLight;
import org.example.enums.LightColor;

public class RedState implements SignalState{
    @Override
    public void handle(TrafficLight context) {
        context.setColor(LightColor.RED);
        context.setNextState(new RedState());
    }
}
