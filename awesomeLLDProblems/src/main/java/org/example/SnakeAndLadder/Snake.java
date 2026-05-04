package org.example.SnakeAndLadder;

public class Snake extends BoardEntity{
    public Snake(int start, int end) {
        super(start, end);
        if(start <= end) {
            throw new IllegalArgumentException("For snakes start should be greater than end");
        }
    }
}
