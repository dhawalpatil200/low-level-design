package org.example.TicTacToe.entities;


import java.util.Optional;

public class Board {
    private Player[][] grid;
    private int size;

    public Board( int size) {
        this.size = size;
        grid = new Player[size][size];
    }

    public void updateBoard(int rowIdx, int colIdx, Player player) {
        if(isValidCell(rowIdx, colIdx) && grid[rowIdx][colIdx] == null) {
            grid[rowIdx][colIdx] = player;
        }
    }

    public Player getPlayerAt(int rowIdx, int colIdx) {
        return isValidCell(rowIdx, colIdx) ? grid[rowIdx][colIdx] : null;
    }

    public boolean isFull() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(grid[i][j] == null) return false;
            }
        }
        return true;
    }

    public Optional<Player> getWinner() {
        // check rows and cols
        boolean isWinner = true;
        for(int i = 0; i < size; i++) {
            if(checkLine(i, 0, 0, 1)) return Optional.of(grid[i][0]);
            if(checkLine(0, i, 1, 0)) return Optional.of(grid[0][i]);
        }

        // check diagonals
        if(checkLine(0, 0, 1, 1)) return Optional.of(grid[0][0]);
        if(checkLine(0, size - 1, 1, -1)) return Optional.of(grid[0][size - 1]);

        return Optional.empty();
    }

    private boolean checkLine(int startRow, int startCol, int rowStep, int colStep) {
        Player player = grid[startCol][startCol];
        if(player == null) {
            return false;
        }

        for(int k = 1; k < size; k++) {
            int currRow = startRow + k * rowStep;
            int currCol = startCol + k * colStep;
            if(grid[currRow][currCol] != player) return false;
        }
        return true;
    }

    private boolean isValidCell(int rowIdx, int colIdx) {
        return (rowIdx >= 0 && rowIdx < size && colIdx >= 0 && colIdx < size);
    }

    public void reset(int size) {
        this.size = size;
        grid = new Player[size][size];
    }
}
