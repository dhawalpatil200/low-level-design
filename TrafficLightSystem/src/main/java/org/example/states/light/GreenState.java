package org.example.states.light;

import org.example.TrafficLight;
import org.example.enums.LightColor;

public class GreenState implements SignalState{
    @Override
    public void handle(TrafficLight context) {
        context.setColor(LightColor.GREEN);
        context.setNextState(new YellowState());
    }
}
