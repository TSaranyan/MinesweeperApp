package minesweeper.service;

import minesweeper.model.Board;
import minesweeper.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {

    private BoardService boardService;
    private Board board;

    @BeforeEach
    void setUp() {
        NotificationService notificationService = Mockito.mock(NotificationService.class);
        boardService = new BoardService(notificationService);
        board = new Board(3, 1); // 3x3 board with 1 mine

        // Initialize grid
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board.getGrid()[r][c] = new Cell();
            }
        }

        // Place a mine at (0,0) and calculate adjacents manually for test
        board.getGrid()[0][0].setMine(true);
        board.getGrid()[0][1].setAdjacentMines(1);
        board.getGrid()[1][0].setAdjacentMines(1);
        board.getGrid()[1][1].setAdjacentMines(1);
    }

    @Test
    void reveal_ShouldReturnFalse_ForInvalidCoordinate() {
        assertFalse(boardService.reveal(board, "Z99"));
        assertFalse(boardService.reveal(board, "A4"));
    }

    @Test
    void reveal_ShouldSetGameOver_WhenMineIsRevealed() {
        assertTrue(boardService.reveal(board, "A1")); // A1 corresponds to (0,0)
        assertTrue(board.isGameOver());
    }

    @Test
    void reveal_ShouldRevealSingleCell_WhenItHasAdjacentMines() {
        boardService.reveal(board, "B2"); // B2 is (1,1), has 1 adjacent mine
        assertTrue(board.getGrid()[1][1].isRevealed());
        assertEquals(1, board.getRevealedCount());
    }

    @Test
    void reveal_ShouldCascadeReveal_WhenCellHasZeroAdjacentMines() {
        // C3 is (2,2), has 0 adjacent mines
        boardService.reveal(board, "C3");

        // Cascade should reveal all non-mine cells
        assertTrue(board.getGrid()[0][1].isRevealed());
        assertTrue(board.getGrid()[1][0].isRevealed());
        assertTrue(board.getGrid()[1][1].isRevealed());
        assertTrue(board.getGrid()[1][2].isRevealed());
        assertTrue(board.getGrid()[2][1].isRevealed());
        assertTrue(board.getGrid()[2][2].isRevealed());

        // Check that mine at (0,0) is not revealed
        assertFalse(board.getGrid()[0][0].isRevealed());
    }
}