package minesweeper.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    private final InputStream originalIn = System.in;
    private ByteArrayInputStream testIn;

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    void restoreSystemInput() {
        System.setIn(originalIn);
    }

    @Test
    void testPromptGridSize_ValidInput() {
        provideInput("4");
        PlayerService playerService = new PlayerService();
        int size = playerService.promptGridSize();
        assertEquals(4, size);
    }

    @Test
    void testPromptGridSize_InvalidThenValid() {
        provideInput("abc1n5");
        PlayerService playerService = new PlayerService();
        int size = playerService.promptGridSize();
        assertEquals(5, size);
    }

    @Test
    void testPromptMines_ValidInput() {
        provideInput("3");
        PlayerService playerService = new PlayerService();
        int mines = playerService.promptMines(4);
        assertEquals(3, mines);
    }

    @Test
    void testPromptMines_InvalidThenValid() {
        provideInput("0102");
        PlayerService playerService = new PlayerService();
        int mines = playerService.promptMines(3);
        assertEquals(2, mines);
    }

    @Test
    void testPromptMove() {
        provideInput(" a1 ");
        PlayerService playerService = new PlayerService();
        String move = playerService.promptMove();
        assertEquals("A1", move);
    }
}
