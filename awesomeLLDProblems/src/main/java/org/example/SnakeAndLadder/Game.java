package org.example.SnakeAndLadder;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;
    private int currentPlayerIdx;
    private Player winner;
    private GameStatus gameStatus;
    private final Dice dice;
    private final Board board;


    private Game(Builder builder) {
        this.board = builder.board;
        this.dice = builder.dice;
        this.players = builder.players;
        this.currentPlayerIdx = 0;
        gameStatus = GameStatus.IN_PROGRESS;
    }

    public void simulate() {
        gameStatus = GameStatus.IN_PROGRESS;
        System.out.println("[Game][Started...]");
        while (gameStatus.equals(GameStatus.IN_PROGRESS)) {
            Player player = getCurrentPlayer();
            this.makeMove(player);
        }
    }

    public synchronized void makeMove(Player player) {
        if(gameStatus != GameStatus.IN_PROGRESS) {
            System.out.println("[Game][Invalid Game state...]");
            throw new IllegalStateException("Game is not in IN_PROGRESS state");
        }

        if(player != getCurrentPlayer()) {
            System.out.println("[Game][Invalid Player initiated move ...]");
            throw new IllegalStateException("Please make move of current player");
        }

        int roll = dice.roll();
        System.out.printf("[Game][Player = {%s}][Rolled a dice = {%d}]%n", player.getName(), roll);
        System.out.printf("[Game][Player = {%s}][Current position = {%d}]%n", player.getName(), player.getPosition());
        int nextPosition = player.getPosition() + roll;
        int updatedPosition = board.getUpdatedPosition(nextPosition);

        // Just for logger
        if(updatedPosition < nextPosition) {
            System.out.printf("[Game][Player = {%s}][Snake][From %d to %d]%n", player.getName(), nextPosition, updatedPosition);
        } else if(updatedPosition > nextPosition) {
            System.out.printf("[Game][Player = {%s}][Ladder][From %d to %d]%n", player.getName(), nextPosition, updatedPosition);
        }


        // if jumps greater than 100 then don't consider
        if(updatedPosition <= board.getSize()) {
            player.setPosition(updatedPosition);
            System.out.printf("[Game][Player = {%s}][Updated position = {%d}]%n", player.getName(), player.getPosition());
        } else {
            System.out.printf("[Game][Player = {%s}][Out of board, Invalid position = {%d}]%n", player.getName(), updatedPosition);
        }

        // check win condition
        if(updatedPosition == board.getSize()) {
            gameStatus = GameStatus.ENDED;
            winner = player;
            System.out.printf("[Game][Player = {%s}][Won the game]%n", player.getName());
            return;
        }

        // if 6(max val) occurs then player instantly get one chance
        if(roll < dice.getMaxVal()) {
            currentPlayerIdx = (currentPlayerIdx + 1) % players.size();
        } else {
            System.out.printf("[Game][Player = {%s}][Rolled the dice = {%d}][Got one more chance...]%n", player.getName(), roll);
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }

    public Player getWinner() {
        return winner;
    }

    public static class Builder {
        private Dice dice;
        private List<Player> players;
        private int size;
        private Board board;
        private List<BoardEntity> boardEntities = new ArrayList<>();

        public Builder setDice(Dice dice) {
            this.dice = dice;
            return this;
        }

        public Builder setEntities(List<BoardEntity> entities) {
            this.boardEntities = entities;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Game build() {
            if(boardEntities == null || players == null || dice == null) {
                throw new IllegalStateException("Board, Players, and Dice must be added.");
            }

            this.board = new Board(this.boardEntities, size);
            return new Game(this);
        }
    }


}
