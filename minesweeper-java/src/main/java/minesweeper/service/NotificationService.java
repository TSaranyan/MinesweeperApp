package minesweeper.service;

import java.io.PrintStream;

public class NotificationService {

    private final PrintStream out;

    public NotificationService(PrintStream out) {
        this.out = out;
    }

    public void sendMessageInSameLine(String value) {
        out.print(value);
    }

    public void sendMessage(String value) {
        out.println(value);
    }
}