package ch.uzh.ifi.hase.soprafs24.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.PlayerRepository;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private SpotifyService spotifyService;

    @InjectMocks
    private GameService gameService;

    // check if injected spotifyService is not null
    @Test
    public void spotifyServiceNotNull() {
        assertNotNull(spotifyService);
    }

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game();
        Player spy = new Player();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        spy.setId(1L);
        player1.setId(2L);
        player2.setId(3L);
        player3.setId(4L);

        game.setGameId("gameId");
        game.setPlayers(Arrays.asList(spy, player1, player2, player3));

        when(gameRepository.findByGameId("gameId")).thenReturn(game);
        when(spotifyService.authenticate()).thenReturn("token");
        when(spotifyService.searchSong(any(), any(), any(), any())).thenReturn(Arrays.asList(
            new SongInfo("Spy Song", "Spy Artist", "SpyImageUrl", "SpyPlayUrl"),
            new SongInfo("Regular Song", "Regular Artist", "RegularImageUrl", "RegularPlayUrl")
        ));
    }

    public void assignSongsAndSpy_assignsCorrectly() {
        
        gameService.assignSongsAndSpy("gameId");

        // Verify that all players have a song info set, and the spy has a different song
        assertNotNull(game.getPlayers().get(0).getSongInfo(), "Spy should have a song set");
        assertEquals("Spy Song", game.getPlayers().get(0).getSongInfo().getTitle(), "Spy should have the spy song");

        for (int i = 1; i < game.getPlayers().size(); i++) {
            assertEquals("Regular Song", game.getPlayers().get(i).getSongInfo().getTitle(), "Non-spies should have the regular song");
        }
    }
}
