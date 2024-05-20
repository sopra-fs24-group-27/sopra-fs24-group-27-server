package ch.uzh.ifi.hase.soprafs24.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
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
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.PlayerSongInfoDTO;
import ch.uzh.ifi.hase.soprafs24.security.TokenUtils;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

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

    @MockBean
    private GameRepository gameRepository;

    @InjectMocks
    private GameController gameController;

    @Test
    public void testCreateRoom_Success() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("existingUser");
        String token = "existingToken";
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setGameId("game-123456");

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

        game.setHostId(host.getId());

        Player hostPlayer = new Player();
        hostPlayer.setUser(host);
        hostPlayer.setHost(true);

        game.setPlayers(Collections.singletonList(hostPlayer));

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
                .andExpect(jsonPath("$.currentRound").value(1))
                .andExpect(jsonPath("$.gameId").value("game-123456"))
                .andExpect(jsonPath("$.hostId").value(1));
    }

    @Test
    public void testJoinRoomAsHost_Success() throws Exception {
        Game game = new Game();
        game.setGameId("game-123456");

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
        String token = "existingToken";
        host.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(host));

        game.setHostId(host.getId());

        Player hostPlayer = new Player();
        hostPlayer.setUser(host);
        hostPlayer.setHost(true);

        game.getPlayers().add(hostPlayer);

        when(gameService.joinRoom(anyString(), anyLong())).thenReturn(game);

        mockMvc.perform(post("/games/game-123456/join")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.settings.market").value("US"))
                .andExpect(jsonPath("$.settings.artist").value("Maroon 5"))
                .andExpect(jsonPath("$.settings.genre").value("Pop"))
                .andExpect(jsonPath("$.currentRound").value(1))
                .andExpect(jsonPath("$.hostId").value(1));
    }

    @Test
    public void testJoinRoomAsSecondPlayer_Success() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setUsername("existingUser");
        String token = "existingToken";
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setGameId("game-123456");

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

        game.setHostId(host.getId());

        Player hostPlayer = new Player();
        hostPlayer.setUser(host);
        hostPlayer.setHost(true);

        Player player = new Player();
        player.setUser(user);
        player.setHost(false);

        game.getPlayers().add(hostPlayer);
        game.getPlayers().add(player);

        when(gameService.joinRoom(anyString(), anyLong())).thenReturn(game);

        mockMvc.perform(post("/games/game-123456/join")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "2"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.settings.market").value("US"))
                .andExpect(jsonPath("$.settings.artist").value("Maroon 5"))
                .andExpect(jsonPath("$.settings.genre").value("Pop"))
                .andExpect(jsonPath("$.currentRound").value(1))
                .andExpect(jsonPath("$.hostId").value(1));
    }

    @Test
    public void testLeaveRoomAsSecondPlayer_Success() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setUsername("existingUser");
        String token = "existingToken";
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setGameId("game-123456");

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

        game.setHostId(host.getId());

        Player hostPlayer = new Player();
        hostPlayer.setUser(host);
        hostPlayer.setHost(true);

        game.getPlayers().add(hostPlayer);

        when(gameService.quit(anyString(), anyLong())).thenReturn(game);

        mockMvc.perform(post("/games/game-123456/quit")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("playerId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.settings.market").value("US"))
                .andExpect(jsonPath("$.settings.artist").value("Maroon 5"))
                .andExpect(jsonPath("$.settings.genre").value("Pop"))
                .andExpect(jsonPath("$.currentRound").value(1))
                .andExpect(jsonPath("$.hostId").value(1));
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
    public void testGetGame_Success() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setUsername("existingUser");
        String token = "existingToken";
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setGameId("game-123456");
        game.setHostId(1L);

        when(gameService.getGameStatus(anyString())).thenReturn(game);

        mockMvc.perform(get("/games/game-123456")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value("game-123456"))
                .andExpect(jsonPath("$.hostId").value("1"));

    }

    @Test
    public void testGetGame_GameNotFound() {

        String gameId = "nonexistentGameId";
        when(gameService.getGameStatus(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        GameController gameController = new GameController(gameService);

        assertThrows(ResponseStatusException.class, () -> gameController.getGame(gameId));

    }

    @Test
    public void testListen_Success() throws Exception {
        String gameId = "game-123456";
        Long playerId = 1L;
        String token = "existingToken";

        User user = new User();
        user.setId(playerId);
        user.setUsername("user");
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        Player player = new Player();
        player.setId(playerId);
        player.setUser(user);
        player.setSpy(false);

        PlayerSongInfoDTO songInfoDTO = new PlayerSongInfoDTO();
        songInfoDTO.setSpy(false);

        when(gameService.getPlayerById(gameId, playerId)).thenReturn(player);
        when(gameService.createPlayerSongInfoDTO(player)).thenReturn(songInfoDTO);

        mockMvc.perform(get("/games/" + gameId + "/listen/" + playerId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spy").value(false));
    }

    @Test
    public void testListen_PlayerNotFound() throws Exception {
        String gameId = "game-123456";
        Long playerId = 1L;
        String token = "existingToken";

        User user = new User();
        user.setId(playerId);
        user.setUsername("user");
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        when(gameService.getPlayerById(gameId, playerId)).thenReturn(null);

        mockMvc.perform(get("/games/" + gameId + "/listen/" + playerId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetSongs_Success() throws Exception {
        String gameId = "game-123456";
        Long playerId = 1L;
        String token = "existingToken";

        User user = new User();
        user.setId(playerId);
        user.setUsername("user");
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        PlayerSongInfoDTO songInfoDTO = new PlayerSongInfoDTO();
        songInfoDTO.setSpy(false);

        List<PlayerSongInfoDTO> songs = Arrays.asList(songInfoDTO);

        when(gameService.getSongs(gameId)).thenReturn(songs);

        mockMvc.perform(get("/games/" + gameId + "/songs")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spy").value(false));
    }

    @Test
    public void testVote_Success() throws Exception {
        String gameId = "game-123456";
        Long voterId = 1L;
        Long votedPlayerId = 2L;
        String token = "existingToken";

        User user = new User();
        user.setId(voterId);
        user.setUsername("user");
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        mockMvc.perform(post("/games/" + gameId + "/vote")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("voterId", voterId.toString())
                .content(votedPlayerId.toString()))
                .andExpect(status().isOk());

        verify(gameService, times(1)).vote(gameId, voterId, votedPlayerId);
    }

    @Test
    public void testVote_PlayerNotFound() throws Exception {
        String gameId = "game-123456";
        Long voterId = 1L;
        Long votedPlayerId = 2L;
        String token = "existingToken";

        User user = new User();
        user.setId(voterId);
        user.setUsername("user");
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(gameService).vote(gameId, voterId, votedPlayerId);

        mockMvc.perform(post("/games/" + gameId + "/vote")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("voterId", voterId.toString())
                .content(votedPlayerId.toString()))
                .andExpect(status().isNotFound());

        verify(gameService, times(1)).vote(gameId, voterId, votedPlayerId);
    }

    @Test
    public void testVote_SelfVote() throws Exception {
        String gameId = "game-123456";
        Long voterId = 1L;
        Long votedPlayerId = 1L; // Voting for self
        String token = "existingToken";

        User user = new User();
        user.setId(voterId);
        user.setUsername("user");
        user.setToken(token);

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(gameService).vote(gameId, voterId, votedPlayerId);

        mockMvc.perform(post("/games/" + gameId + "/vote")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("voterId", voterId.toString())
                .content(votedPlayerId.toString()))
                .andExpect(status().isBadRequest());

        verify(gameService, times(1)).vote(gameId, voterId, votedPlayerId);
    }
}