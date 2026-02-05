package org.example.states.intersection;

import org.example.IntersectionController;
import org.example.enums.Direction;
import org.example.enums.LightColor;

public class EastWestGreenState implements IntersectionState{
    @Override
    public void handle(IntersectionController context) throws InterruptedException{
        System.out.printf("\n--- INTERSECTION %d: Cycle -> East-West GREEN ---\n", context.getId());

        context.getLight(Direction.EAST).startGreen();
        context.getLight(Direction.WEST).startGreen();
        context.getLight(Direction.NORTH).setColor(LightColor.RED);
        context.getLight(Direction.SOUTH).setColor(LightColor.RED);

        Thread.sleep(context.getGreenDuration());

        context.getLight(Direction.EAST).transition();
        context.getLight(Direction.WEST).transition();

        Thread.sleep(context.getYellowDuration());

        context.getLight(Direction.EAST).transition();
        context.getLight(Direction.WEST).transition();

        context.setState(new NorthSouthGreenState());
    }
}
