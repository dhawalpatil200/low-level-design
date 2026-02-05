package org.example.observers;

import org.example.enums.Direction;
import org.example.enums.LightColor;

public interface TrafficObserver {
    void update(int intersectionId, Direction direction, LightColor lightColor);
}
