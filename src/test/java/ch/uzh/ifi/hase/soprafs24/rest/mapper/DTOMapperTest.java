package ch.uzh.ifi.hase.soprafs24.rest.mapper;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.VotePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.PlayerInfoDTO;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
  @Test
  public void testCreateUser_fromUserPostDTO_toUser_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("username");
    userPostDTO.setPassword("password");
    userPostDTO.setBirthDate(null);

    // MAP -> Create user
    User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // check content
    assertEquals(userPostDTO.getUsername(), user.getUsername());
  }

  @Test
  public void testGetUser_fromUser_toUserGetDTO_success() {
    // create User
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setStatus(UserStatus.OFFLINE);
    user.setToken("1");
    user.setBirthDate(null);

    // MAP -> Create UserGetDTO
    UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

    // check content
    assertEquals(user.getId(), userGetDTO.getId());
    assertEquals(user.getUsername(), userGetDTO.getUsername());
    assertEquals(user.getStatus(), userGetDTO.getStatus());
    assertEquals(user.getToken(), userGetDTO.getToken());
    assertEquals(user.getBirthDate(), userGetDTO.getBirthDate());
  }

  @Test
  public void testCreateGame_fromGamePostDTO_toGame_success() {
    // create GamePostDTO
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setPassword("password");
    Player player = new Player();
    player.setUser(user);
    player.setHost(true);
    player.setScore(0);
    Settings settings = new Settings();
    settings.setMarket("US");
    settings.setArtist("Maroon 5");
    settings.setGenre("Pop");
    GamePostDTO gamePostDTO = new GamePostDTO();
    gamePostDTO.setGameId("1");
    gamePostDTO.setHostId(user.getId());
    gamePostDTO.setSettings(settings);
    gamePostDTO.setCurrentRound(1);
    gamePostDTO.getPlayers().add(player);

    // map GamePostDTO to Game
    Game game = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);

    // check content
    assertEquals(gamePostDTO.getGameId(), game.getGameId());
    assertEquals(gamePostDTO.getHostId(), game.getHostId());
    assertEquals(gamePostDTO.getCurrentRound(), game.getCurrentRound());
    assertEquals(gamePostDTO.getPlayers(), game.getPlayers());
    assertEquals(gamePostDTO.getSettings().getMarket(), game.getSettings().getMarket());
    assertEquals(gamePostDTO.getSettings().getArtist(), game.getSettings().getArtist());
    assertEquals(gamePostDTO.getSettings().getGenre(), game.getSettings().getGenre());
  }

  @Test
  public void testGetGame_fromGame_toGameGetDTO_success() {
    // create Game
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setPassword("password");
    Player player = new Player();
    player.setUser(user);
    player.setHost(true);
    player.setScore(0);
    Settings settings = new Settings();
    settings.setMarket("US");
    settings.setArtist("Maroon 5");
    settings.setGenre("Pop");
    Game game = new Game();
    game.setGameId("1");
    game.setHostId(user.getId());
    game.setSettings(settings);
    game.setCurrentRound(1);
    game.getPlayers().add(player);

    // map Game to GameGetDTO
    GameGetDTO gameGetDTO = DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);

    // check content
    assertEquals(game.getGameId(), gameGetDTO.getGameId());
    assertEquals(game.getHostId(), gameGetDTO.getHostId());
    assertEquals(game.getCurrentRound(), gameGetDTO.getCurrentRound());
    assertEquals(game.getPlayers(), gameGetDTO.getPlayers());
    assertEquals(game.getSettings().getMarket(), gameGetDTO.getSettings().getMarket());
    assertEquals(game.getSettings().getArtist(), gameGetDTO.getSettings().getArtist());
    assertEquals(game.getSettings().getGenre(), gameGetDTO.getSettings().getGenre());
  }

  @Test
  public void testPlayerInfoDTO_ConstructorAndGetters() {
    PlayerInfoDTO playerInfoDTO = new PlayerInfoDTO(1L, 1L, "firstname@lastname", "avatar_url", 100, Arrays.asList("😀", "🎉"), 1);

    assertEquals(1L, playerInfoDTO.getId());
    assertEquals(1L, playerInfoDTO.getUserId());
    assertEquals("firstname@lastname", playerInfoDTO.getUsername());
    assertEquals("avatar_url", playerInfoDTO.getAvatar());
    assertEquals(100, playerInfoDTO.getScore());
    assertEquals(Arrays.asList("😀", "🎉"), playerInfoDTO.getEmojis());
    assertEquals(1, playerInfoDTO.getTurn());
  }


  @Test
  public void testCreatePlayerInfoDTO_fromPlayer_success() {
    User user = new User();
    user.setId(1L);
    user.setUsername("firstname@lastname");
    user.setPassword("password");
    Player player = new Player();
    player.setUser(user);
    player.setHost(true);
    player.setScore(100);
    player.setId(1L);
    player.setEmojis(Arrays.asList("😀", "🎉"));
    player.setTurn(1);

    PlayerInfoDTO playerInfoDTO = new PlayerInfoDTO();
    playerInfoDTO.setId(player.getId());
    playerInfoDTO.setUserId(player.getUser().getId());
    playerInfoDTO.setUsername(player.getUser().getUsername());
    playerInfoDTO.setAvatar(player.getAvatar());
    playerInfoDTO.setScore(player.getScore());
    playerInfoDTO.setEmojis(player.getEmojis());
    playerInfoDTO.setTurn(player.getTurn());

    assertEquals(player.getId(), playerInfoDTO.getId());
    assertEquals(player.getUser().getId(), playerInfoDTO.getUserId());
    assertEquals(player.getUser().getUsername(), playerInfoDTO.getUsername());
    assertEquals(player.getAvatar(), playerInfoDTO.getAvatar());
    assertEquals(player.getScore(), playerInfoDTO.getScore());
    assertEquals(player.getEmojis(), playerInfoDTO.getEmojis());
    assertEquals(player.getTurn(), playerInfoDTO.getTurn());
  }

  @Test
    public void testVotePostDTO_SettersAndGetters() {
        VotePostDTO votePostDTO = new VotePostDTO();
        
        votePostDTO.setPlayerId(1L);

        assertEquals(1L, votePostDTO.getPlayerId());
    }

    @Test
    public void testVotePostDTO_Constructor() {
        VotePostDTO votePostDTO = new VotePostDTO();
        votePostDTO.setPlayerId(2L);

        assertEquals(2L, votePostDTO.getPlayerId());
    }
  
}
