package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.service.SpotifyService;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    private final PlayerRepository playerRepository;

    private final UserRepository userRepository;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
            @Qualifier("playerRepository") PlayerRepository playerRepository,
            @Qualifier("userRepository") UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
    }

    private boolean checkIfPlayerExists(Long id) {
        return playerRepository.existsById(id);
    }

    private Player getPlayerById(Long userId) {
        if (checkIfPlayerExists(userId)) {
            return playerRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            Player player = new Player();
            player.setUserId(user.getId());
            player.setUsername(user.getUsername());
            player.setAvatar("avatar"); // set default avatar
            player.setScore(0); // set default score
            player = playerRepository.save(player);
            playerRepository.flush();
            return player;
        }
    }

    public Game getGameById(String gameId) {
        return gameRepository.findByGameId(gameId);
    }

    public Game createRoom(Game newRoom) {
        newRoom.setCurrentRound(1);
        Player host = getPlayerById(newRoom.getHostId());
        List<Player> players = new ArrayList<>();
        players.add(host);
        newRoom.setPlayers(players);
        log.debug("Created room: {}", newRoom);
        return newRoom;
    }

    // Only host can delete the game by pressing Quit button
    public void deleteRoom(String gameId) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        gameRepository.delete(gameByGameId);
    }

    public Game joinRoom(String gameId, Player player) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        List<Player> players = gameByGameId.getPlayers();
        players.add(player);
        gameByGameId.setPlayers(players);
        return gameByGameId;
    }

    public Game modifySettings(String gameId, Settings settings) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        gameByGameId.setSettings(settings);
        return gameByGameId;
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
        Game game = gameRepository.findByGameId(gameId);
        return game.getPlayers();
    }

    public Player getPlayerById(String gameId, Long playerId) {
        Game game = gameRepository.findByGameId(gameId);
        return game.getPlayers().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player not found in game: " + gameId));
    }

    public Player getPlayerByUsername(String gameId, String username) {
        Game game = gameRepository.findByGameId(gameId);
        return game.getPlayers().stream()
                .filter(player -> player.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player not found in game: " + gameId));
    }

    public Game assignSongsAndSpy(String gameId) {
        Game game = gameRepository.findByGameId(gameId);
        if (game == null) {
            log.error("Game not found with ID: {}", gameId);
            return null; // Handle game not found scenario
        }

        List<Player> players = game.getPlayers();
        if (players == null || players.isEmpty()) {
            log.error("No players found for game ID: {}", gameId);
            return game; // Handle no players scenario
        }

        Collections.shuffle(players); // Randomize players list to select a spy randomly

        Player spy = players.get(0); // Select the first player as the spy after shuffling
        String token = spotifyService.authenticate(); // Authenticate with Spotify
        if (token == null) {
            log.error("Failed to authenticate with Spotify");
            return game; // Consider how to handle authentication failure in game flow
        }
        log.info("Successfully authenticated with Spotify");

        List<SongInfo> songs;
        try {
            songs = spotifyService.searchSong("English", "pop", "Maroon 5", token); // Search for songs
            if (songs.size() < 2) {
                log.error("Not enough songs found for the game setup");
                return game; // Handle scenario where not enough songs are found
            }
        } catch (RuntimeException ex) {
            log.error("Error during song search: {}", ex.getMessage());
            return game; // Handle search error scenario
        }

        // Assign the first song to the spy and the second song to all others
        for (Player player : players) {
            if (player.equals(spy)) {
                player.setSongInfo(songs.get(0)); // Different song for the spy
            } else {
                player.setSongInfo(songs.get(1)); // Same song for all non-spies
            }
        }
        playerRepository.saveAll(players); // Save all player details
        return game;
    }

    public Game sortTurnOrder(String gameId) {
        Game game = gameRepository.findByGameId(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        List<Player> players = game.getPlayers();
        Collections.shuffle(players); // Shuffle the list to randomize turn order
        game.setPlayers(players); // Set the shuffled list back to the game
        return gameRepository.save(game); // Save the changes to the database
    }

    public Game startRound(String gameId) {
        Game game = assignSongsAndSpy(gameId); // Assign songs and spy
        if (game != null) {
            game.setCurrentRound(game.getCurrentRound() + 1); // Increment the round number
            gameRepository.save(game); // Save the updated game state
        }
        return game;
    }

    public Game sendEmojis(String gameId, Player player, List<String> emojis) {
        Game game = gameRepository.findByGameId(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        player.setEmojis(emojis); // Set the emojis sent by the player
        playerRepository.save(player); // Save the player's updated state
        return game;
    }

    public Game vote(String gameId, Long votedPlayerId) {
        Game game = gameRepository.findByGameId(gameId);
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
        Game game = gameRepository.findByGameId(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        return game;
    }

    public Game declareWinner(String gameId) {
        Game game = gameRepository.findByGameId(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        Player winner = game.getPlayers().stream().max(Comparator.comparing(Player::getVotes)).orElse(null);
        game.setWinner(winner);
        return gameRepository.save(game);
    }

    public Game endRound(String gameId) {
        Game game = gameRepository.findByGameId(gameId);
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
        Game gameByGameId = gameRepository.findByGameId(gameId);
        List<Player> players = gameByGameId.getPlayers();
        players.remove(player);
        gameByGameId.setPlayers(players);
        return gameByGameId;
    }

}
