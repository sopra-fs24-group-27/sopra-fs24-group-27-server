package ch.uzh.ifi.hase.soprafs24.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;


import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;

import java.util.Collections;


@WebMvcTest(value = GameController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCreateRoom() throws Exception {
        Game game = new Game();
        // Sample settings as provided by client
        Settings settings = new Settings();
        settings.setLanguage("English");
        settings.setArtist("Maroon 5");
        settings.setGenre("Pop");
    
        // Assume default settings for gameId and currentRound for now, adjust if necessary
        game.setSettings(settings);
        game.setCurrentRound(1);  // Assuming a default current round
        
        // use mock user as current user and host
        User host = new User();
        host.setId(1L);
        host.setUsername("user");
        host.setStatus(UserStatus.ONLINE);
    
        Player hostPlayer = new Player();
        hostPlayer.setUser(host);
        hostPlayer.setHost(true);
        game.setPlayers(Collections.singletonList(hostPlayer));
        game.setHostId(host.getId());
    
        when(gameService.createRoom(any(GamePostDTO.class), any(Long.class))).thenReturn(game);
    
        // Construct the JSON payload as it should be received from the client
        String jsonContent = """
            {
                "settings": {
                    "language": "English",
                    "artist": "Maroon 5",
                    "genre": "Pop"
                },
                "currentRound": 0
            }
            """;
    
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .param("userId", "1")) // userId provided as request parameter
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.settings.language").value("English"))
                .andExpect(jsonPath("$.settings.artist").value("Maroon 5"))
                .andExpect(jsonPath("$.settings.genre").value("Pop"))
                .andExpect(jsonPath("$.currentRound").value(1));
    }
    
    //@Test
    //public void testJoinGame() throws Exception {
    //    Game game = new Game();
    //    game.setGameId("gameId");
    //    game.setPlayers(Collections.singletonList(new Player()));
    //    when(gameService.joinRoom("gameId", 1L)).thenReturn(game);

    //    mockMvc.perform(post("/games/{gameId}/join", "gameId")
    //                    .param("userId", "1")
    //                    .contentType(MediaType.APPLICATION_JSON))
    //            .andExpect(status().isOk())
    //            .andExpect(jsonPath("$.gameId").value("gameId"));
    //}

    //@Test
    //public void testJoinGamePlayerAlreadyInGame() throws Exception {
    //    when(gameService.isPlayerInAnyGame(1L)).thenReturn(true);
    //
    //    mockMvc.perform(post("/games/{gameId}/join", "gameId")
    //                    .param("userId", "1")
    //                    .contentType(MediaType.APPLICATION_JSON))
    //            .andExpect(status().isBadRequest());
    //}

    //@Test
    //public void testStartGame() throws Exception {
    //    mockMvc.perform(post("/games/{gameId}/start", "gameId")
    //                    .contentType(MediaType.APPLICATION_JSON))
    //            .andExpect(status().isOk());
    //}
}
