package minesweeper.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void isGameWon_ReturnsTrue_WhenAllNonMineCellsAreRevealed() {
        Board board = new Board(2, 1);
        board.setRevealedCount(3); // 2*2 grid - 1 mine = 3 cells to reveal
        assertTrue(board.isGameWon());
    }

    @Test
    void isGameWon_ReturnsFalse_WhenNotAllNonMineCellsAreRevealed() {
        Board board = new Board(2, 1);
        board.setRevealedCount(2);
        assertFalse(board.isGameWon());
    }
}