package minesweeper.creator;

import minesweeper.model.Board;
import minesweeper.model.Cell;
import minesweeper.service.BoardService;

public class BoardCreator {

    private final BoardService boardService;
    private final MinePlacementStrategy minePlacementStrategy;

    public BoardCreator(BoardService boardService, MinePlacementStrategy minePlacementStrategy) {
        this.boardService = boardService;
        this.minePlacementStrategy = minePlacementStrategy;
    }

    public Board initialize(int size, int mines) {
        Board board = new Board(size, mines);
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board.getGrid()[r][c] = new Cell();
            }
        }
        minePlacementStrategy.placeMines(board);
        calculateAdjacents(board);
        return board;
    }

    private void calculateAdjacents(Board board) {
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (!board.getGrid()[r][c].isMine()) {
                    board.getGrid()[r][c].setAdjacentMines(countMinesAround(board, r, c));
                }
            }
        }
    }

    private int countMinesAround(Board board, int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = row + dr, nc = col + dc;
                if (boardService.inBounds(board, nr, nc) && board.getGrid()[nr][nc].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }
}