package minesweeper.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void sendMessage_ShouldPrintMessageWithNewline() {
        NotificationService notificationService = new NotificationService(System.out);
        notificationService.sendMessage("Hello");
        assertEquals("Hello" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void sendMessageInSameLine_ShouldPrintMessageWithoutNewline() {
        NotificationService notificationService = new NotificationService(System.out);
        notificationService.sendMessageInSameLine("Hello");
        assertEquals("Hello", outContent.toString());
    }
}