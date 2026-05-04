package org.example.SnakeAndLadder;

import java.util.Arrays;
import java.util.List;

public class SnakeAndLaddersDemo {
    public static void main(String[] args) {
        Game game = new Game.Builder()
                .setDice(new Dice(1, 6))
                .setPlayers(createPlayers())
                .setSize(5)
                .setEntities(createEntities())
                .build();

        game.simulate();
        Player winner = game.getWinner();
        System.out.printf("Winner = %s", winner.getName());


    }

    private static List<BoardEntity> createEntities() {
        return Arrays.asList(new Snake(10, 4), new Ladder(2, 6), new Snake(4, 1), new Ladder(11, 18));
    }

    private static List<Player> createPlayers() {
        Player player1 = new Player("John");
        Player player2 = new Player("Jay");
        return Arrays.asList(player1, player2);
    }
}
