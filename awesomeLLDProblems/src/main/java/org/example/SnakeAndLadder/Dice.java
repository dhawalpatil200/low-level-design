package org.example.SnakeAndLadder;

public class Dice {
    private final int minVal;
    private final int maxVal;

    public Dice(int minVal, int maxVal) {
        if(minVal >= maxVal) throw new IllegalArgumentException("minVal should be smaller than maxVal");

        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    public int roll() {
        int size = maxVal - minVal + 1;
        return minVal + (int)(Math.random() * size);
    }

    public int getMaxVal() {
        return this.maxVal;
    }
}
