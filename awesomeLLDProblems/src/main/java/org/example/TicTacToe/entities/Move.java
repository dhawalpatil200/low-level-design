package org.example.TicTacToe.entities;

public class Move {
    int rowIdx;
    int colIdx;
    Player player;

    public Move(int rowIdx, int colIdx, Player player) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.player = player;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }

    public Player getPlayer() {
        return player;
    }
}
