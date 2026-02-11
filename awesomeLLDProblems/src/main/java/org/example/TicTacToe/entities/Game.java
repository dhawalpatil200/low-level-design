package org.example.TicTacToe.entities;

import java.util.Optional;

public class Game {
    private final Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private MoveHistory moveHistory;

    public Game(Player A, Player B) {
        board = new Board(3);
        startNewGame(A, B);
        moveHistory = new MoveHistory();
    }

    public void startNewGame(Player a, Player b) {
        board.reset(3);
        players = new Player[]{a, b};
        currentPlayerIndex = 0;
    }

    public void makeMove(int rowIdx, int colIdx, Player player) {
        // handle game ended
        if(getGameStatus().equals(GameCondition.ENDED)) {
            throw new IllegalArgumentException("Game already ended");
        }

        // handle invalid move (already occupied)
        if(player != getCurrentPlayer()) {
            throw new IllegalArgumentException("Invalid player attempted move");
        }

        if(board.getPlayerAt(rowIdx, colIdx) != null) {
            throw new IllegalArgumentException("Board already taken");
        }

        // update board
        board.updateBoard(rowIdx, colIdx, player);
        // publish the move
        moveHistory.recordMove(new Move(rowIdx, colIdx, player));
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    GameCondition getGameStatus() {
        if(board.getWinner().isPresent() || board.isFull()) return GameCondition.ENDED;
        return GameCondition.IN_PROGRESS;
    }

    Player getWinner() {
        Optional<Player> winner = board.getWinner();
        return winner.orElse(null);
    }

    private Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }
}
