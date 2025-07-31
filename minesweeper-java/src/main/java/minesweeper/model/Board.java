package minesweeper.model;

public class Board {
    private final int size;
    private final int mineCount;
    private Cell[][] grid;
    private boolean gameOver;
    private int revealedCount = 0;

    public Board(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.grid = new Cell[size][size];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getSize() {
        return size;
    }

    public int getMineCount() {
        return mineCount;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getRevealedCount() {
        return revealedCount;
    }

    public void setRevealedCount(int revealedCount) {
        this.revealedCount = revealedCount;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameWon() {
        return revealedCount == (size * size - mineCount);
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }
}
