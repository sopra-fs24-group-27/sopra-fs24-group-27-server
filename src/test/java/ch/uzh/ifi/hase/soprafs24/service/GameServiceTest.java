package ch.uzh.ifi.hase.soprafs24.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.repository.SongInfoRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.PlayerSongInfoDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SongInfoRepository songInfoRepository;
    @Mock
    private SpotifyService spotifyService;

    @InjectMocks
    private GameService gameService;

    private String dynamicGameId;

    @BeforeEach
    public void setup() {
        // Manual setting of mocks if @Autowired is not effective in the test context
        gameService = new GameService(gameRepository, playerRepository, userRepository, songInfoRepository);
        gameService.setSpotifyService(spotifyService);

        User host = new User();
        host.setId(1L);

        Player hostPlayer = new Player();
        hostPlayer.setId(1L);
        hostPlayer.setUser(host);
        hostPlayer.setHost(true);

        // Use lenient() for stubs that might not be used in every test
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(host));
        lenient().when(playerRepository.findByUserId(1L)).thenReturn(Optional.of(hostPlayer));
        lenient().when(playerRepository.save(any(Player.class))).thenReturn(hostPlayer);
        lenient().when(gameRepository.findByGameIdWithPlayers(dynamicGameId)).thenReturn(Optional.empty());

        // Ensure we handle dynamic game IDs correctly by returning the same game that
        // was saved
        lenient().when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
            Game savedGame = invocation.getArgument(0);
            dynamicGameId = savedGame.getGameId(); // Capture dynamically generated game ID
            lenient().when(gameRepository.findByGameIdWithPlayers(dynamicGameId)).thenReturn(Optional.of(savedGame));
            return savedGame;
        });

    }

    @Test
    public void testCreateRoom_ShouldCreateRoom() {
        Settings settings = new Settings();
        settings.setMarket("US");
        settings.setArtist("Maroon 5");
        settings.setGenre("Pop");

        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setHostId(1L);
        gamePostDTO.setSettings(settings);

        // Assuming the mapper is working correctly and we have stubbed
        // gameRepository.save() to return the saved entity
        Game createdGame = gameService.createRoom(gamePostDTO);

        assertNotNull(createdGame, "Game should not be null");
        dynamicGameId = createdGame.getGameId();
        assertNotNull(dynamicGameId, "Game ID should not be null");
        assertTrue(createdGame.getPlayers().stream().anyMatch(Player::isHost), "Should have a host player");
        verify(gameRepository).save(createdGame);

        assertEquals(createdGame, gameRepository.findByGameIdWithPlayers(dynamicGameId).get(),
                "Game should be saved in the repository");
    }

    @Test
    public void testAddHost_ShouldAddPlayerToRoom() {
        testCreateRoom_ShouldCreateRoom(); // Ensure room is created and ID is set

        User newUser = new User();
        newUser.setId(1L);

        Game updatedGame = gameService.joinRoom(dynamicGameId, 1L);

        assertNotNull(updatedGame, "Game should not be null");
        assertEquals(1, updatedGame.getPlayers().size(), "Should have at least one player now");
        assertEquals(updatedGame, gameRepository.findByGameIdWithPlayers(dynamicGameId).get(),
                "Game should be saved in the repository");
    }

    @Test
    public void testJoinRoom_ShouldAddPlayerToRoom() {
        testCreateRoom_ShouldCreateRoom(); // Ensure room is created and ID is set

        User newUser = new User();
        newUser.setId(2L);

        // Stubbing the userRepository to return a new user for ID 2L
        when(userRepository.findById(2L)).thenReturn(Optional.of(newUser));

        Player newPlayer = new Player();
        newPlayer.setId(2L);
        newPlayer.setUser(newUser);

        // Assuming no player exists yet for userId 2L, so create a new player
        when(playerRepository.findByUserId(2L)).thenReturn(Optional.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(newPlayer);

        Game updatedGame = gameService.joinRoom(dynamicGameId, 2L);

        assertNotNull(updatedGame, "Game should not be null");
        assertEquals(2, updatedGame.getPlayers().size(), "Should have 2 players now");
        assertTrue(updatedGame.getPlayers().contains(newPlayer), "New player should be added to the game");

        assertEquals(updatedGame, gameRepository.findByGameIdWithPlayers(dynamicGameId).get(),
                "Game should be saved in the repository");
    }

    @Test
    public void testAddThirdPlayer_ShouldAddPlayerToRoom() {
        testJoinRoom_ShouldAddPlayerToRoom(); // Ensure a player is added to the room

        User user3 = new User();
        user3.setId(3L);
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));

        Player player3 = new Player();
        player3.setId(3L);
        player3.setUser(user3);

        when(playerRepository.findByUserId(3L)).thenReturn(Optional.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(player3);

        Game updatedGame = gameService.joinRoom(dynamicGameId, 3L);

        assertNotNull(updatedGame, "Game should not be null");
        assertEquals(3, updatedGame.getPlayers().size(), "Should have 3 players now");
        assertTrue(updatedGame.getPlayers().contains(player3), "New player should be added to the game");

        assertEquals(updatedGame, gameRepository.findByGameIdWithPlayers(dynamicGameId).get(),
                "Game should be saved in the repository");
    }

    @Test
    public void testAddFourthPlayer_ShouldAddPlayerToRoom() {
        testAddThirdPlayer_ShouldAddPlayerToRoom(); // Ensure a player is added to the room

        User user4 = new User();
        user4.setId(4L);
        when(userRepository.findById(4L)).thenReturn(Optional.of(user4));

        Player player4 = new Player();
        player4.setId(4L);
        player4.setUser(user4);

        when(playerRepository.findByUserId(4L)).thenReturn(Optional.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(player4);

        Game updatedGame = gameService.joinRoom(dynamicGameId, 4L);

        assertNotNull(updatedGame, "Game should not be null");
        assertEquals(4, updatedGame.getPlayers().size(), "Should have 4 players now");
        assertTrue(updatedGame.getPlayers().contains(player4), "New player should be added to the game");

        assertEquals(updatedGame, gameRepository.findByGameIdWithPlayers(dynamicGameId).get(),
                "Game should be saved in the repository");
    }

    // @Test
    // public void testStartGame_ShouldStartGameWithSufficientPlayers() {
    // testAddFourthPlayer_ShouldAddPlayerToRoom(); // Ensure 4 players are added to
    // the room

    // when(spotifyService.authenticate()).thenReturn("valid-token");
    // when(spotifyService.searchSong(
    // eq("English"),
    // eq("Pop"),
    // eq("Maroon 5"),
    // eq("valid-token")
    // )).thenReturn(Arrays.asList(
    // new SongInfo("Sugar", "Maroon 5", "image-url", "play-url"),
    // new SongInfo("Memories", "Maroon 5", "image-url", "play-url")
    // ));

    // Call startGame to simulate the game starting
    // Game startedGame = gameService.startGame(dynamicGameId);

    // Verify that authenticate was called
    // verify(spotifyService).authenticate();

    // assertNotNull(startedGame, "Game should not be null after starting");
    // assertEquals(1, startedGame.getCurrentRound(), "Game should be on the first
    // round after starting");
    // assertTrue(startedGame.getPlayers().stream().anyMatch(Player::isSpy), "Should
    // have a spy player");
    // assertEquals("Sugar",
    // startedGame.getPlayers().get(0).getSongInfo().getTitle(), "Spy should have
    // the correct song");
    // assertEquals("Memories",
    // startedGame.getPlayers().get(1).getSongInfo().getTitle(), "The second player
    // should have the second song");
    // assertEquals("Memories",
    // startedGame.getPlayers().get(2).getSongInfo().getTitle(), "The third player
    // should have the second song");
    // assertEquals("Memories",
    // startedGame.getPlayers().get(3).getSongInfo().getTitle(), "The fourth player
    // should have the second song");
    // }

    
    @Test
    public void testStartGame_GameNotFound() {

        String gameId = "nonexistentGameId";
        when(gameRepository.findByGameIdWithPlayers(anyString())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> gameService.startGame(gameId));
    }

    @Test
    public void testStartGame_NotEnoughPlayers() {

        String gameId = "existingGameId";
        Game game = new Game();
        game.setPlayers(new ArrayList<>());
        when(gameRepository.findByGameIdWithPlayers(anyString())).thenReturn(Optional.of(game));

        assertThrows(IllegalStateException.class, () -> gameService.startGame(gameId));
    }

    @Test
    public void testStartGame_SpotifyAuthenticationFailed() {

        String gameId = "existingGameId";
        Game game = new Game();
        game.setPlayers(Arrays.asList(new Player(), new Player(), new Player(), new Player())); 
        when(gameRepository.findByGameIdWithPlayers(anyString())).thenReturn(Optional.of(game));
        when(spotifyService.authenticate()).thenReturn(null); // Simulating authentication failure

        assertThrows(IllegalStateException.class, () -> gameService.startGame(gameId));
    }

    @Test
    public void testStartGame_Success() {

        String gameId = "game-123456";
        Game game = new Game();
        Settings settings = new Settings();
        settings.setMarket("US");
        settings.setArtist("Maroon 5");
        settings.setGenre("Pop");
        game.setSettings(settings);
        game.setPlayers(Arrays.asList(new Player(), new Player(), new Player(), new Player())); // 4 players
        when(gameRepository.findByGameIdWithPlayers(Mockito.anyString())).thenReturn(Optional.of(game));
        when(spotifyService.authenticate()).thenReturn("mockedToken"); 
        when(spotifyService.searchSong(eq("US"), eq("Pop"), eq("Maroon 5"), anyString()))
                .thenReturn(Arrays.asList(new SongInfo(), new SongInfo())); 

        Game startedGame = gameService.startGame(gameId);

        assertEquals(1, startedGame.getCurrentRound()); 
        verify(gameRepository, times(1)).save(game); 
        }

    @Test
    public void testSortTurnOrder_ShouldSortPlayersTurnOrder() {
        Game game = new Game();
        game.setGameId("test-game");
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Player player = new Player();
            player.setId((long) i);
            players.add(player);
        }
        game.setPlayers(players);

        when(gameRepository.findByGameIdWithPlayers("test-game")).thenReturn(Optional.of(game));

        Game sortedGame = gameService.sortTurnOrder("test-game");

        List<Player> sortedPlayers = sortedGame.getPlayers();
        assertNotNull(sortedPlayers);
        assertEquals(4, sortedPlayers.size());
        for (int i = 0; i < sortedPlayers.size(); i++) {
            assertEquals(i + 1, sortedPlayers.get(i).getTurn());
        }

        verify(gameRepository).save(game);
    }

    @Test
    public void testSavePlayerEmojis_ShouldSaveEmojis() {
        Game game = new Game();
        game.setGameId("test-game");
        game.setCurrentTurn(1);
        game.setCurrentRound(1);
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Player player = new Player();
            player.setId((long) i);
            player.setTurn(i);
            player.setGame(game);
            players.add(player);
        }
        game.setPlayers(players);

        when(gameRepository.findByGameIdWithPlayers("test-game")).thenReturn(Optional.of(game));

        List<String> emojis = Arrays.asList("😀", "🎉");

        gameService.savePlayerEmojis("test-game", 1L, emojis, 1);

        assertEquals(emojis, players.get(0).getEmojis());
        assertEquals(2, game.getCurrentTurn());

        verify(playerRepository).save(players.get(0));
        verify(gameRepository).save(game);
    }

    @Test
    public void testGetGameByIdWithPlayers_Success() {

        String gameId = "game-123456";
        Game expectedGame = new Game();
        when(gameRepository.findByGameIdWithPlayers(eq(gameId))).thenReturn(Optional.of(expectedGame));

        Game retrievedGame = gameService.getGameByIdWithPlayers(gameId);
        assertEquals(expectedGame, retrievedGame);
    }

    @Test
    public void testGetGameByIdWithPlayers_GameNotFound() {

        String gameId = "nonexistentGameId";
        when(gameRepository.findByGameIdWithPlayers(eq(gameId))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> gameService.getGameByIdWithPlayers(gameId));
    }


}
