package org.example.SnakeAndLadder;

public class Ladder extends BoardEntity{
    public Ladder(int start, int end) {
        super(start, end);
        if(start >= end) {
            throw new IllegalArgumentException("For ladders start should be lesser than end");
        }
    }
}
