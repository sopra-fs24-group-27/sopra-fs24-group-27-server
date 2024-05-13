package ch.uzh.ifi.hase.soprafs24.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.security.TokenUtils;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;

import java.util.Collections;

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

    @Test
    @WithMockUser(username = "user")
    public void testCreateRoom() throws Exception {
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .param("userId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.settings.market").value("US"))
                .andExpect(jsonPath("$.settings.artist").value("Maroon 5"))
                .andExpect(jsonPath("$.settings.genre").value("Pop"))
                .andExpect(jsonPath("$.currentRound").value(1));
    }
}
