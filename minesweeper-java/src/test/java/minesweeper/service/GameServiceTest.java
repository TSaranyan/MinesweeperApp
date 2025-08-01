package minesweeper.service;

import minesweeper.creator.BoardCreator;
import minesweeper.model.Board;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static minesweeper.constant.GameConstant.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private NotificationService notificationService;
    @Mock
    private BoardCreator boardCreator;
    @Mock
    private PlayerService playerService;
    @Mock
    private BoardService boardService;

    @InjectMocks
    private GameService gameService;

    @Test
    void start_ShouldRunGame_AndEndInLoss() {
        // Arrange
        Board board = new Board(2, 1);
        when(playerService.promptGridSize()).thenReturn(2);
        when(playerService.promptMines(2)).thenReturn(1);
        when(boardCreator.initialize(2, 1)).thenReturn(board);

        // Simulate a game where the user hits a mine
        when(playerService.promptMove()).thenReturn("A1");
        when(boardService.reveal(board, "A1")).thenAnswer(invocation -> {
            board.setGameOver(true);
            return true;
        });

        // Act
        gameService.start();

        // Assert
        verify(notificationService).sendMessage(WELCOME);
        verify(notificationService).sendMessage(GAME_OVER);
        verify(notificationService).sendMessage(PLAY_AGAIN);
        verify(playerService).waitForPlayAgain();
    }

    @Test
    void start_ShouldRunGame_AndEndInWin() {
        // Arrange
        Board board = new Board(2, 1);
        when(playerService.promptGridSize()).thenReturn(2);
        when(playerService.promptMines(2)).thenReturn(1);
        when(boardCreator.initialize(2, 1)).thenReturn(board);

        // Simulate a game where the user wins
        when(playerService.promptMove()).thenReturn("A2", "B1", "B2");
        when(boardService.reveal(any(Board.class), anyString())).thenAnswer(invocation -> {
            board.setRevealedCount(board.getRevealedCount() + 1);
            // After the 3rd valid move, the game is won
            if (board.getRevealedCount() == 3) {
                // isGameWon() will be true now
            }
            return true;
        });

        // Mock the board's isGameWon method to simulate winning
        // This is tricky as the state is inside the loop. A better way is to control the board's state.
        // Let's control the state directly after the reveal call.
        doAnswer(invocation -> {
            when(board.isGameWon()).thenReturn(true);
            return null;
        }).when(boardService).reveal(board, "B2");

        // Act
        gameService.start();

        // Assert
        verify(notificationService).sendMessage(WELCOME);
        verify(notificationService).sendMessage(GAME_WON);
        verify(notificationService).sendMessage(PLAY_AGAIN);
    }
}