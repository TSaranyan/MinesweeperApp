package minesweeper.service;

import java.util.Scanner;

public class PlayerService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptGridSize() {
        int size;
        while (true) {
            System.out.print("Enter the size of the grid (e.g. 4 for 4x4): ");
            String input = scanner.nextLine();
            try {
                size = Integer.parseInt(input);
                if (size >= 2 && size <= 26) return size;
            } catch (Exception ignored) {}
            System.out.println("Invalid grid size. Try again.");
        }
    }

    public int promptMines(int size) {
        int maxMines = (int) (size * size * 0.35);
        int mines;
        while (true) {
            System.out.printf("Enter number of mines (max %d): ", maxMines);
            String input = scanner.nextLine();
            try {
                mines = Integer.parseInt(input);
                if (mines > 0 && mines <= maxMines) return mines;
            } catch (Exception ignored) {}
            System.out.println("Invalid mine count. Try again.");
        }
    }

    public String promptMove() {
        System.out.print("Select a square to reveal (e.g. A1): ");
        return scanner.nextLine().toUpperCase().trim();
    }
}
