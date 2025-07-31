package minesweeper.service;

import minesweeper.model.Board;
import minesweeper.model.Cell;

import java.util.LinkedList;
import java.util.Queue;

import static minesweeper.constant.GameConstant.EMPTY;
import static minesweeper.constant.GameConstant.EMPTY_SPACE;
import static minesweeper.constant.GameConstant.STAR;
import static minesweeper.constant.GameConstant.UNDER_SCORE;

public class BoardService {

    private final NotificationService notificationService;
    public BoardService(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    public boolean reveal(Board board, String coord) {
        int row = coord.charAt(0) - 'A';
        int col;
        try {
            col = Integer.parseInt(coord.substring(1)) - 1;
        } catch (Exception e) {
            return false;
        }

        if (!inBounds(board, row, col) || board.getGrid()[row][col].isRevealed()){
            return false;
        }

        if (board.getGrid()[row][col].isMine()) {
            board.getGrid()[row][col].setRevealed(true);
            board.setGameOver(true);
            return true;
        }

        revealCells(board, row, col);
        return true;
    }

    private void revealCells(Board board, int row, int col) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{row, col});

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];

            if (!inBounds(board, r, c) || board.getGrid()[r][c].isRevealed()) continue;

            board.getGrid()[r][c].setRevealed(true);
            board.setRevealedCount(board.getRevealedCount()+1);

            if (board.getGrid()[r][c].getAdjacentMines() == 0) {
                for (int dr = -1; dr <= 1; dr++){
                    for (int dc = -1; dc <= 1; dc++){
                        if (!(dr == 0 && dc == 0)){
                            q.add(new int[]{r + dr, c + dc});
                        }
                    }
                }
            }
        }
    }

    public void printBoard(Board board, boolean showMines) {
        notificationService.sendMessageInSameLine(EMPTY_SPACE);
        for (int c = 1; c <= board.getSize(); c++) {
            notificationService.sendMessageInSameLine(c + EMPTY);
        }
        System.out.println();

        for (int r = 0; r < board.getSize(); r++) {
            notificationService.sendMessageInSameLine((char) ('A' + r) + EMPTY);
            for (int c = 0; c < board.getSize(); c++) {
                Cell cell = board.getGrid()[r][c];
                if (cell.isRevealed()) {
                    String value = cell.isMine() ? STAR : cell.getAdjacentMines() + EMPTY;
                    notificationService.sendMessageInSameLine(value);
                } else {
                    notificationService.sendMessageInSameLine(showMines && cell.isMine() ? STAR : UNDER_SCORE);
                }
            }
            System.out.println();
        }
    }

    public boolean inBounds(Board board, int row, int col) {
        int size = board.getSize();
        return row >= 0 && row < size && col >= 0 && col < size;
    }

}
