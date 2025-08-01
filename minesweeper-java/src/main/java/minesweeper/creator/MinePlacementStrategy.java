package minesweeper.creator;

import minesweeper.model.Board;

public interface MinePlacementStrategy {
    void placeMines(Board board);
}