package minesweeper.creator;

import minesweeper.model.Board;
import java.util.Random;

public class RandomMinePlacementStrategy implements MinePlacementStrategy {

    private final Random random;

    public RandomMinePlacementStrategy() {
        this.random = new Random();
    }

    // Constructor for testing with a seeded random
    public RandomMinePlacementStrategy(Random random) {
        this.random = random;
    }

    @Override
    public void placeMines(Board board) {
        int placed = 0;
        while (placed < board.getMineCount()) {
            int r = random.nextInt(board.getSize());
            int c = random.nextInt(board.getSize());
            if (!board.getGrid()[r][c].isMine()) {
                board.getGrid()[r][c].setMine(true);
                placed++;
            }
        }
    }
}