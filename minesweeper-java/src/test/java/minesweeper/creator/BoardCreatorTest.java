package minesweeper.creator;

import minesweeper.model.Board;
import minesweeper.model.Cell;
import minesweeper.service.BoardService;
import minesweeper.service.NotificationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardCreatorTest {

    // A fixed strategy for predictable tests
    static class FixedMinePlacementStrategy implements MinePlacementStrategy {
        @Override
        public void placeMines(Board board) {
            // Place a mine at (0, 0)
            board.getGrid()[0][0].setMine(true);
        }
    }

    @Test
    void initialize_ShouldCreateBoardWithCorrectSizeAndMines() {
        BoardService boardService = new BoardService(null); // Not used in this specific test flow
        BoardCreator boardCreator = new BoardCreator(boardService, new FixedMinePlacementStrategy());

        Board board = boardCreator.initialize(2, 1);

        assertEquals(2, board.getSize());
        assertEquals(1, board.getMineCount());
        assertNotNull(board.getGrid());
        assertTrue(board.getGrid()[0][0].isMine());
        assertFalse(board.getGrid()[0][1].isMine());
    }

    @Test
    void initialize_ShouldCalculateCorrectAdjacentMines() {
        BoardService boardService = new BoardService(null);
        BoardCreator boardCreator = new BoardCreator(boardService, new FixedMinePlacementStrategy());

        Board board = boardCreator.initialize(2, 1); // Mine at (0,0)

        // Check adjacent counts
        assertEquals(1, board.getGrid()[0][1].getAdjacentMines());
        assertEquals(1, board.getGrid()[1][0].getAdjacentMines());
        assertEquals(1, board.getGrid()[1][1].getAdjacentMines());
    }
}