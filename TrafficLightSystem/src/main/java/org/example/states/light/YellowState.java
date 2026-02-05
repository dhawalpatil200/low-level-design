package org.example.states.light;

import org.example.TrafficLight;
import org.example.enums.LightColor;

public class YellowState implements SignalState{
    @Override
    public void handle(TrafficLight context) {
        context.setColor(LightColor.YELLOW);
        context.setNextState(new RedState());
    }
}
