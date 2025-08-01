package minesweeper.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerServiceTest {

    @Test
    void promptGridSize_ShouldReturnValidSize_AfterInvalidAttempts() {
        String simulatedInput = "1\n30\ninvalid\n4\n";
        Scanner scanner = new Scanner(simulatedInput);
        NotificationService notificationService = new NotificationService(new PrintStream(new ByteArrayOutputStream()));

        PlayerService playerService = new PlayerService(scanner, notificationService);
        int size = playerService.promptGridSize();

        assertEquals(4, size);
    }

    @Test
    void promptMines_ShouldReturnValidMineCount() {
        String simulatedInput = "0\n11\ninvalid\n5\n"; // Max for 10x10 is 35
        Scanner scanner = new Scanner(simulatedInput);
        NotificationService notificationService = new NotificationService(new PrintStream(new ByteArrayOutputStream()));

        PlayerService playerService = new PlayerService(scanner, notificationService);
        int mines = playerService.promptMines(10);

        assertEquals(5, mines);
    }

    @Test
    void promptMove_ShouldReturnUppercasedTrimmedInput() {
        String simulatedInput = " a1 \n";
        Scanner scanner = new Scanner(simulatedInput);
        NotificationService notificationService = Mockito.mock(NotificationService.class);

        PlayerService playerService = new PlayerService(scanner, notificationService);
        String move = playerService.promptMove();

        assertEquals("A1", move);
    }
}