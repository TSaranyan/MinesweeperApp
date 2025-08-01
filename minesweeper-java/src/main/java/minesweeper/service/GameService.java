package minesweeper.service;

import minesweeper.creator.BoardCreator;
import minesweeper.model.Board;

import static minesweeper.constant.GameConstant.GAME_OVER;
import static minesweeper.constant.GameConstant.GAME_WON;
import static minesweeper.constant.GameConstant.INVALID;
import static minesweeper.constant.GameConstant.PLAY_AGAIN;
import static minesweeper.constant.GameConstant.WELCOME;

public class GameService {
    private final NotificationService notificationService;
    private final BoardCreator boardCreator;

    private final BoardService boardService;

    private final PlayerService playerService;


    public GameService(NotificationService notificationService, BoardCreator boardCreator, PlayerService playerService, BoardService boardService){
        this.notificationService = notificationService;
        this.boardCreator = boardCreator;
        this.playerService=playerService;
        this.boardService = boardService;
    }

    public void start() {
        notificationService.sendMessage(WELCOME);
        int size = playerService.promptGridSize();
        int mines = playerService.promptMines(size);

        Board board = boardCreator.initialize(size, mines);

        while (!board.isGameOver() && !board.isGameWon()) {
            boardService.printBoard(board, false);
            String input = playerService.promptMove();
            boolean validMove = boardService.reveal(board, input);
            if (!validMove) {
                notificationService.sendMessage(INVALID);
            }
        }

        boardService.printBoard(board, true);

        if (board.isGameOver()) {
            notificationService.sendMessage(GAME_OVER);
        } else {
            notificationService.sendMessage(GAME_WON);
        }

        notificationService.sendMessage(PLAY_AGAIN);

        playerService.waitForPlayAgain();
    }
}
