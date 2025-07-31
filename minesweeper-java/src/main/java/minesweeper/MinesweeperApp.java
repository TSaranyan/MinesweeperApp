package minesweeper;

import minesweeper.creator.BoardCreator;
import minesweeper.service.BoardService;
import minesweeper.service.GameService;
import minesweeper.service.NotificationService;
import minesweeper.service.PlayerService;

public class MinesweeperApp {

    public static void main(String[] args) {
        final NotificationService notificationService = new NotificationService();
        final BoardService boardService = new BoardService(notificationService);
        final BoardCreator boardCreator = new BoardCreator(boardService);
        final PlayerService playerService =  new PlayerService();

        final GameService gameService = new GameService(notificationService, boardCreator, playerService, boardService);

        while (true) {
            gameService.start();
        }
    }
}
