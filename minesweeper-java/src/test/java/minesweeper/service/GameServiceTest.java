package minesweeper.service;

import minesweeper.creator.BoardCreator;
import minesweeper.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class GameServiceTest {

    private NotificationService notificationService;
    private BoardCreator boardCreator;
    private PlayerService playerService;
    private BoardService boardService;

    private GameService gameService;

    private Board board;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        boardCreator = mock(BoardCreator.class);
        playerService = mock(PlayerService.class);
        boardService = mock(BoardService.class);

        gameService = new GameService(notificationService, boardCreator, playerService, boardService);

        board = mock(Board.class);
    }

    @Test
    void testStartGame_WinScenario() {
        when(playerService.promptGridSize()).thenReturn(3);
        when(playerService.promptMines(3)).thenReturn(1);
        when(boardCreator.initialize(3, 1)).thenReturn(board);

        when(board.isGameOver()).thenReturn(false, false, false); // Not game over
        when(board.isGameWon()).thenReturn(false, false, true);   // Finally won

        when(playerService.promptMove()).thenReturn("A1");
        when(boardService.reveal(board, "A1")).thenReturn(true);

        gameService.start();

        InOrder inOrder = inOrder(notificationService, boardService, playerService);

        inOrder.verify(notificationService).sendMessage("Welcome to Minesweeper!");
        inOrder.verify(boardService, times(3)).printBoard(board, false);
        inOrder.verify(playerService, times(1)).promptMove();
        inOrder.verify(notificationService).sendMessage("Congratulations! You have won the game.");
        inOrder.verify(notificationService).sendMessage("Do you want to play again? (y/n):");
        inOrder.verify(playerService).promptMove();
    }

    @Test
    void testStartGame_InvalidMoveThenGameOver() {
        when(playerService.promptGridSize()).thenReturn(3);
        when(playerService.promptMines(3)).thenReturn(1);
        when(boardCreator.initialize(3, 1)).thenReturn(board);

        when(board.isGameOver()).thenReturn(false, false, true);
        when(board.isGameWon()).thenReturn(false);

        when(playerService.promptMove()).thenReturn("Z9");
        when(boardService.reveal(board, "Z9")).thenReturn(false); // invalid move

        gameService.start();

        verify(notificationService).sendMessage("Invalid move. Please try again.");
        verify(notificationService).sendMessage("Game Over! You hit a mine.");
        verify(notificationService).sendMessage("Do you want to play again? (y/n):");
    }
}
