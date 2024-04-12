package ch.uzh.ifi.hase.soprafs24.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.GameResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class GameWebSocketControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private GameWebSocketController controller;

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game();
        Player spy = new Player();
        spy.setId(1L);
        Player player1 = new Player();
        player1.setId(2L);
        Player player2 = new Player();
        player2.setId(3L);
        Player player3 = new Player();
        player3.setId(4L);
    
        game.setGameId("gameId");
        game.setPlayers(Arrays.asList(spy, player1, player2, player3));
    
        when(gameService.startRound("gameId")).thenReturn(game);
    
        when(gameService.startRound("gameId")).thenReturn(game);

        // If the controller uses constructor injection
        controller = new GameWebSocketController(gameService);
        controller.setSimpMessagingTemplate(messagingTemplate);  // Ensure this method exists or add it as suggested above
    }

    @Test
    public void testStartRound() {
        controller.startRound("gameId");
    
        // Verify that the gameService.startRound method was called with the correct gameId
        verify(gameService, times(1)).startRound("gameId");
    
        // Verify that general game info is broadcasted to all players
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/games/gameId"), any(GameResponseDTO.class));
    
        // Verify that individual game info is sent to each player
        verify(messagingTemplate, times(game.getPlayers().size()))
            .convertAndSendToUser(anyString(), eq("/queue/roundStart"), any(GameResponseDTO.class));
    }
}