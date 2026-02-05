package org.example.states.intersection;

import org.example.IntersectionController;

public interface IntersectionState {
    void handle(IntersectionController context) throws InterruptedException;
}
