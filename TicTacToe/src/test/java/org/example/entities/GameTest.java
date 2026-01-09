package org.example.entities;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// NOTE: Assumes methods like makeMove and getGameStatus are accessible for testing
// In a real scenario, you would test public methods that drive the game state.

public class GameTest {

    @Test
    void test_player_A_wins_on_row() {
        // Setup players
        Player playerA = new Player("Alice", 'X');
        Player playerB = new Player("Bob", 'O');

        // Initialize game
        Game game = new Game(playerA, playerB);

        // --- Game Moves ---
        // Player A (X) moves (0,0)
        game.makeMove(0, 0, playerA);
        // Player B (O) moves (1,0)
        game.makeMove(1, 0, playerB);
        // Player A (X) moves (0,1)
        game.makeMove(0, 1, playerA);
        // Player B (O) moves (1,1)
        game.makeMove(1, 1, playerB);

        // Player A (X) makes the winning move (0,2)
        game.makeMove(0, 2, playerA);

        // --- Assertion ---
        // Verify the game status is ENDED
        assertEquals(GameCondition.ENDED, game.getGameStatus(),
                "Game status should be ENDED after a player wins.");

         assertEquals(game.getWinner(), playerA);
    }

    @Test
    void test_game_ends_in_draw() {
        // Setup players
        Player playerA = new Player("Alice", 'X');
        Player playerB = new Player("Bob", 'O');

        // Initialize game
        Game game = new Game(playerA, playerB);

        // --- Draw Move Sequence ---
        // Board state after all 9 moves:
        // O | O | X
        // X | O | O
        // O | X | X

        game.makeMove(0, 0, playerA); // X
        game.makeMove(1, 1, playerB); // O
        game.makeMove(0, 2, playerA); // X
        game.makeMove(0, 1, playerB); // O
        game.makeMove(2, 1, playerA); // X
        game.makeMove(2, 0, playerB); // O
        game.makeMove(1, 0, playerA); // X
        game.makeMove(1, 2, playerB); // O
        game.makeMove(2, 2, playerA); // X - Final move, board full

        // --- Assertion ---
        // Verify the game status is ENDED
        assertEquals(GameCondition.ENDED, game.getGameStatus(),
                "Game status should be ENDED when the board is full and there's no winner.");

        // If a public method to check for a winner existed, you'd assert:
        // assertTrue(game.getWinner().isEmpty(), "There should be no winner in a draw.");
    }
}