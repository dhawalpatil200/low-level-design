package org.example.observers;

import org.example.enums.Direction;
import org.example.enums.LightColor;

public class CentralMonitor implements TrafficObserver{
    @Override
    public void update(int intersectionId, Direction direction, LightColor lightColor) {
        System.out.printf("[MONITOR] Intersection %d: Light for %s direction changed to %s.\n",
                intersectionId, direction, lightColor);
    }
}
