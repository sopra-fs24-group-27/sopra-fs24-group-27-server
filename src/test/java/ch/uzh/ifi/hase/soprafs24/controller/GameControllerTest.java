package ch.uzh.ifi.hase.soprafs24.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.security.TokenUtils;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

@WebMvcTest(value = GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private GameController gameController;

    @Test
    @WithMockUser(username = "user")
    public void testCreateRoom() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("existingUser");
        String token = "existingToken";
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        Game game = new Game();
        Settings settings = new Settings();
        settings.setMarket("US");
        settings.setArtist("Maroon 5");
        settings.setGenre("Pop");

        game.setSettings(settings);
        game.setCurrentRound(1);

        User host = new User();
        host.setId(1L);
        host.setUsername("user");
        host.setStatus(UserStatus.ONLINE);

        Player hostPlayer = new Player();
        hostPlayer.setUser(host);
        hostPlayer.setHost(true);
        game.setPlayers(Collections.singletonList(hostPlayer));
        game.setHostId(host.getId());

        when(gameService.createRoom(any(GamePostDTO.class))).thenReturn(game);

        String jsonContent = """
                {
                    "settings": {
                        "market": "US",
                        "artist": "Maroon 5",
                        "genre": "Pop"
                    },
                    "currentRound": 0
                }
                """;

        mockMvc.perform(post("/games")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .param("userId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.settings.market").value("US"))
                .andExpect(jsonPath("$.settings.artist").value("Maroon 5"))
                .andExpect(jsonPath("$.settings.genre").value("Pop"))
                .andExpect(jsonPath("$.currentRound").value(1));
    }

    @Test
    public void testStartGame_Success() {

        String gameId = "game-123456";
        Game game = new Game();
        when(gameService.startGame(anyString())).thenReturn(game);
        GameController gameController = new GameController(gameService);

        GameGetDTO result = gameController.start(gameId);

        assertEquals(game.getId(), result.getGameId());
        verify(gameService, times(1)).startGame(gameId);
    }

    @Test
    public void testStartGame_GameNotFound() {

        String gameId = "nonexistentGameId";
        when(gameService.startGame(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        GameController gameController = new GameController(gameService);

        assertThrows(ResponseStatusException.class, () -> gameController.start(gameId));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetGame_Success() throws Exception {

        String gameId = "game-123456";
        Game game = new Game();
        when(gameService.getGameStatus(anyString())).thenReturn(game);
        GameController gameController = new GameController(gameService);

        GameGetDTO result = gameController.getGame(gameId);
        assertNotNull(result);

    }

    @Test
    public void testGetGame_GameNotFound() {

        String gameId = "nonexistentGameId";
        when(gameService.getGameStatus(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        GameController gameController = new GameController(gameService);

        assertThrows(ResponseStatusException.class, () -> gameController.getGame(gameId));

    }

}