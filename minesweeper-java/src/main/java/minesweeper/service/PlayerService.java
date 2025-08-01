package minesweeper.service;

import java.util.Scanner;
import static minesweeper.constant.GameConstant.*;

public class PlayerService {

    private final Scanner scanner;
    private final NotificationService notificationService;

    public PlayerService(Scanner scanner, NotificationService notificationService) {
        this.scanner = scanner;
        this.notificationService = notificationService;
    }

    public int promptGridSize() {
        while (true) {
            notificationService.sendMessageInSameLine(PROMPT_GRID_SIZE);
            String input = scanner.nextLine();
            try {
                int size = Integer.parseInt(input);
                if (size >= 2 && size <= 26) return size;
            } catch (Exception ignored) {}
            notificationService.sendMessage(INVALID_GRID_SIZE);
        }
    }

    public int promptMines(int size) {
        int maxMines = (int) (size * size * 0.35);
        while (true) {
            notificationService.sendMessageInSameLine(String.format(PROMPT_MINES, maxMines));
            String input = scanner.nextLine();
            try {
                int mines = Integer.parseInt(input);
                if (mines > 0 && mines <= maxMines) return mines;
            } catch (Exception ignored) {}
            notificationService.sendMessage(INVALID_MINE_COUNT);
        }
    }

    public String promptMove() {
        notificationService.sendMessageInSameLine(PROMPT_MOVE);
        return scanner.nextLine().toUpperCase().trim();
    }

    public void waitForPlayAgain() {
        scanner.nextLine();
    }
}