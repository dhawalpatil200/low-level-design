package org.example.TicTacToe.entities;

import java.util.ArrayDeque;

public class MoveHistory {
    ArrayDeque<Move> history;

    public MoveHistory() {
        this.history = new ArrayDeque<>();
    }

    public void recordMove(Move move) {
        history.push(move);
    }

    public Move undo() {
        return history.pop();
    }

    public void clearHistory() {
        history.clear();
    }
}
