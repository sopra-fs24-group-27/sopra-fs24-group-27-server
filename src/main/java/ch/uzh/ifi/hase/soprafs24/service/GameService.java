package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.PlayerInfoDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.PlayerSongInfoDTO;

import org.hibernate.type.TrueFalseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    private final PlayerRepository playerRepository;

    private final UserRepository userRepository;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
            @Qualifier("playerRepository") PlayerRepository playerRepository,
            @Qualifier("userRepository") UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private SpotifyService spotifyService;

    // Setter method for SpotifyService
    public void setSpotifyService(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    // Method to get or create a player from a user ID
    private Player getOrCreatePlayerFromUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        return playerRepository.findByUserId(user.getId()).orElseGet(() -> {
            Player player = new Player();
            player.setScore(0);  // Default score
            player = playerRepository.save(player);
            return player;
        });
    }

    public Game getGameById(String gameId) {
        return gameRepository.findByGameId(gameId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }
    
    public Game createRoom(GamePostDTO gamePostDTO, Long userId) {
        Game newRoom = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);
        newRoom.setGameId("game-" + System.currentTimeMillis());  // Generate a unique game ID
        newRoom.setPlayers(new ArrayList<>());  // Initialize the players list
        // get current user and set it as the host
        User host = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Player hostPlayer = getOrCreatePlayerFromUser(host.getId());
        hostPlayer.setGame(newRoom);
        hostPlayer.setHost(true);
        newRoom.getPlayers().add(hostPlayer);
        newRoom.setHostId(host.getId());
        log.debug("Created room: {}", newRoom);
        return gameRepository.save(newRoom);
    }


    public void deleteRoom(String gameId) {
        Game gameByGameId = gameRepository.findByGameId(gameId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        gameRepository.delete(gameByGameId);
    }
    

    public Game joinRoom(String gameId, Long userId) {
        Game game = gameRepository.findByGameId(gameId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        // check if the user is already in a game
        if (playerRepository.findByUserId(userId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player is already in a game");
        }
        // check if the game already has 4 players
        if (game.getPlayers().size() >= 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game is full");
        }
        Player player = getOrCreatePlayerFromUser(userId);
        game.getPlayers().add(player);
        player.setGame(game);
        player.setHost(false);
        playerRepository.save(player);
        return game;
    }    

    public boolean isPlayerInAnyGame(Long userId) {
        // find if the player is already in a game player.getGame().getGameId() returns not null
        return playerRepository.findByUserId(userId).get().getGame() != null;
    }    

    public List<PlayerInfoDTO> getPlayerInfoForGame(String gameId) {
        Game game = gameRepository.findByGameId(gameId)
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    
        return game.getPlayers().stream()
                   .map(player -> new PlayerInfoDTO(
                          player.getId(),
                          player.getUser().getId(),
                          player.getUser().getUsername(),
                          player.getUser().getAvatar(),
                          player.getScore(),
                          player.getEmojis(),
                          player.getTurn()
                     ))
                     .collect(Collectors.toList());
    }
    
    public PlayerSongInfoDTO createPlayerSongInfoDTO(Player player) {
        SongInfo songInfo = player.getSongInfo();
        if (songInfo != null) {
            return new PlayerSongInfoDTO(
                    player.getId(),
                    player.isSpy(),
                    songInfo.getTitle(),
                    songInfo.getArtist(),
                    songInfo.getPlayUrl(),
                    songInfo.getImageUrl());
        }
        return null; // Handle as needed
    }

    // player related methods: find all players in a game, find one player by id,
    // find one player by username
    public List<Player> getPlayers(String gameId) {
        Game game = gameRepository.findByGameId(gameId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return game.getPlayers();
    }

    public Player getPlayerById(String gameId, Long playerId) {
        Game game = gameRepository.findByGameId(gameId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return game.getPlayers().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player not found in game: " + gameId));
    }

    public Player getPlayerByUsername(String gameId, String username) {
        Game game = gameRepository.findByGameId(gameId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return game.getPlayers().stream()
                .filter(player -> player.getUser().getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player not found in game: " + gameId));
    }

    public Game startGame(String gameId) {
        Game game = gameRepository.findByGameId(gameId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        if (game.getPlayers().size() < 4) {
            throw new IllegalStateException("Cannot start game: Not enough players");
        }

        // Authenticate with Spotify and fetch songs
        String token = spotifyService.authenticate();
        // check if token is empty
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("Failed to authenticate with Spotify");
        }
        // print logs to check if token is fetched
        log.info("Token: " + token);
        List<SongInfo> songs = spotifyService.searchSong(
            game.getSettings().getLanguage(),  // Pass language as the description
            game.getSettings().getGenre(),
            game.getSettings().getArtist(),
            token
        );

        // print logs to check if songs are fetched
        log.info("Searched songs: " + songs);

        // Assign songs and spy
        assignSongsAndSpy(game, songs);

        // Additional logic to officially start game
        game.setCurrentRound(1); // Start rounds
        gameRepository.save(game);

        return game;
    }
    
    private void assignSongsAndSpy(Game game, List<SongInfo> songs) {
        if (songs.size() < 2) {
            throw new IllegalArgumentException("Not enough songs provided to assign to players");
        }
    
        Collections.shuffle(game.getPlayers());
        // Set the first player as the spy
        game.getPlayers().get(0).setSpy(true);
        // Assign the first song to the spy
        game.getPlayers().get(0).setSongInfo(songs.get(0));
    
        // Assign the second song to the rest of the players (players 2, 3, and 4)
        for (int i = 1; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setSongInfo(songs.get(1));
            game.getPlayers().get(i).setSpy(false); // Ensure only the first player is marked as the spy
        }
    
        // Store the updated player information in the repository
        playerRepository.saveAll(game.getPlayers());
    }
    

    public Game sortTurnOrder(String gameId) {
        Game game = gameRepository.findByGameId(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        List<Player> players = game.getPlayers();
        Collections.shuffle(players); // Shuffle the list to randomize turn order
        game.setPlayers(players); // Set the shuffled list back to the game
        return gameRepository.save(game); // Save the changes to the database
    }

    public Game sendEmojis(String gameId, Player player, List<String> emojis) {
        Game game = gameRepository.findByGameId(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        player.setEmojis(emojis); // Set the emojis sent by the player
        playerRepository.save(player); // Save the player's updated state
        return game;
    }

    public Game vote(String gameId, Long votedPlayerId) {
        Game game = gameRepository.findByGameId(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        Player votedPlayer = playerRepository.findById(votedPlayerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voted player not found"));
        votedPlayer.setVotes(votedPlayer.getVotes() + 1); // Increment the votes for the voted player
        playerRepository.save(votedPlayer); // Save the voted player's state
        return game;
    }

    public Game endVoting(String gameId) {
        Game game = gameRepository.findByGameId(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        return game;
    }

    public Game declareWinner(String gameId) {
        Game game = gameRepository.findByGameId(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        Player winner = game.getPlayers().stream().max(Comparator.comparing(Player::getVotes)).orElse(null);
        game.setWinner(winner);
        return gameRepository.save(game);
    }

    public Game endRound(String gameId) {
        Game game = gameRepository.findByGameId(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        // Update scores for all players based on the round results
        for (Player player : game.getPlayers()) {
            // Update logic based on your game rules
            player.setScore(player.getScore() + 1); // Example increment
        }
        playerRepository.saveAll(game.getPlayers());
        return gameRepository.save(game);
    }

    // Players except host can leave the room by pressing Quit button
    public Game leaveRoom(String gameId, Player player) {
        Game gameByGameId = gameRepository.findByGameId(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        List<Player> players = gameByGameId.getPlayers();
        players.remove(player);
        gameByGameId.setPlayers(players);
        return gameByGameId;
    }

}
