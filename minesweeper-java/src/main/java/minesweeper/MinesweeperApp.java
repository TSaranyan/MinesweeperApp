package minesweeper;

import minesweeper.creator.BoardCreator;
import minesweeper.creator.RandomMinePlacementStrategy;
import minesweeper.service.BoardService;
import minesweeper.service.GameService;
import minesweeper.service.NotificationService;
import minesweeper.service.PlayerService;

import java.util.Scanner;

public class MinesweeperApp {

    public static void main(String[] args) {
        final NotificationService notificationService = new NotificationService(System.out);
        final BoardService boardService = new BoardService(notificationService);
        final BoardCreator boardCreator = new BoardCreator(boardService, new RandomMinePlacementStrategy());
        final PlayerService playerService = new PlayerService(new Scanner(System.in), notificationService);
        final GameService gameService = new GameService(notificationService, boardCreator, playerService, boardService);

        while (true) {
            gameService.start();
        }
    }
}