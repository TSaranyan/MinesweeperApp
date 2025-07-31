package minesweeper.service;

import minesweeper.model.Board;
import minesweeper.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {

    private NotificationService notificationService;
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        notificationService = Mockito.mock(NotificationService.class);
        boardService = new BoardService(notificationService);
    }

    private Board createSimpleBoard(int size, int[][] mines) {
        Board board = new Board(size, mines.length);
        Cell[][] grid = new Cell[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                grid[r][c] = new Cell();
            }
        }

        for (int[] mine : mines) {
            grid[mine[0]][mine[1]].setMine(true);
        }

        // Set adjacent mine counts
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (!grid[r][c].isMine()) {
                    int count = 0;
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            int nr = r + dr, nc = c + dc;
                            if (nr >= 0 && nr < size && nc >= 0 && nc < size && grid[nr][nc].isMine()) {
                                count++;
                            }
                        }
                    }
                    grid[r][c].setAdjacentMines(count);
                }
            }
        }

        board.setGrid(grid);
        return board;
    }

    @Test
    void testReveal_InvalidCoordinate() {
        Board board = createSimpleBoard(3, new int[][]{});
        boolean result = boardService.reveal(board, "Z9"); // Out of bounds
        assertFalse(result);
    }

    @Test
    void testReveal_InvalidFormat() {
        Board board = createSimpleBoard(3, new int[][]{});
        boolean result = boardService.reveal(board, "A"); // Missing column number
        assertFalse(result);
    }

    @Test
    void testReveal_AlreadyRevealed() {
        Board board = createSimpleBoard(3, new int[][]{});
        board.getGrid()[0][0].setRevealed(true);
        boolean result = boardService.reveal(board, "A1");
        assertFalse(result);
    }

    @Test
    void testReveal_HitMine_GameOver() {
        Board board = createSimpleBoard(3, new int[][]{{0, 0}});
        boolean result = boardService.reveal(board, "A1");
        assertTrue(result);
        assertTrue(board.isGameOver());
        assertTrue(board.getGrid()[0][0].isRevealed());
    }

    @Test
    void testReveal_EmptyCell_Revealed() {
        Board board = createSimpleBoard(3, new int[][]{{0, 1}});
        boolean result = boardService.reveal(board, "C3");
        assertTrue(result);
        assertTrue(board.getGrid()[2][2].isRevealed());
    }

    @Test
    void testInBounds_True() {
        Board board = createSimpleBoard(3, new int[][]{});
        assertTrue(boardService.inBounds(board, 2, 2));
    }

    @Test
    void testInBounds_False() {
        Board board = createSimpleBoard(3, new int[][]{});
        assertFalse(boardService.inBounds(board, -1, 0));
        assertFalse(boardService.inBounds(board, 0, 3));
    }

    @Test
    void testPrintBoard_NoExceptions() {
        Board board = createSimpleBoard(3, new int[][]{{1, 1}});
        board.getGrid()[0][0].setRevealed(true);
        board.getGrid()[0][0].setAdjacentMines(1);
        boardService.printBoard(board, false);
        Mockito.verify(notificationService, Mockito.atLeastOnce()).sendMessageInSameLine(Mockito.anyString());
    }
}
