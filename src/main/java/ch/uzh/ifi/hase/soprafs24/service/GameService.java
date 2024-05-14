package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.repository.SongInfoRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.EmojiPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.VotePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.PlayerSongInfoDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.SpotifyService;

import org.hibernate.type.TrueFalseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    private final PlayerRepository playerRepository;

    private final UserRepository userRepository;

    private final SongInfoRepository songInfoRepository;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
            @Qualifier("playerRepository") PlayerRepository playerRepository,
            @Qualifier("userRepository") UserRepository userRepository,
            @Qualifier("songInfoRepository") SongInfoRepository songInfoRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.songInfoRepository = songInfoRepository;

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
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return playerRepository.findByUserId(userId).orElseGet(() -> {
            Player player = new Player();
            player.setUser(user); // Make sure to set the user here
            player.setScore(0); // Default score
            return playerRepository.save(player);
        });
    }

    public class UniqueIDGenerator {
        public static String generateID() {
            Random random = new Random();
            int id = 100000 + random.nextInt(900000);
            return String.valueOf(id);
        }
    }

    // Create a game or room
    public Game createRoom(GamePostDTO gamePostDTO) {
        Game newRoom = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);

        newRoom.setGameId("game-" + UniqueIDGenerator.generateID());

        newRoom.setPlayers(new ArrayList<>()); // Initialize the players list
        // get current user and set it as the host
        User host = userRepository.findById(newRoom.getHostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Player hostPlayer = getOrCreatePlayerFromUser(host.getId());
        hostPlayer.setGame(newRoom);
        hostPlayer.setHost(true);
        newRoom.getPlayers().add(hostPlayer);
        newRoom.setHostId(host.getId());
        log.debug("Created room: {}", newRoom);
        return gameRepository.save(newRoom);
    }

    public Game getGameByIdWithPlayers(String gameId) {
        return gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }

    // Get status of the created game or room
    public Game getGameStatus(String gameId) {
        return getGameByIdWithPlayers(gameId);
    }

    // Update the created game or room
    public Game updateRoom(String gameId, GamePostDTO gamePostDTO) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        Game newRoom = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);
        currentRoom.setSettings(newRoom.getSettings());
        return gameRepository.save(currentRoom);
    }

    // Delete the created game or room
    public void deleteRoom(String gameId) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        gameRepository.delete(currentRoom);
    }

    // Delete the joined player, including host, from the created game or room,
    // if the player is the host, delete the game or room
    public Game quit(String gameId, Long playerId) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        currentRoom.getPlayers().removeIf(player -> player.getId().equals(playerId));
        // if (currentRoom.getHostId().equals(playerId)) {
        // gameRepository.delete(currentRoom);
        // }
        gameRepository.save(currentRoom);
        return currentRoom;
    }

    // Assign a song to each player, each player can only fetch their assigned one
    public Game listen(String gameId, Long playerId) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        return currentRoom;
    }

    // Refresh current emoji round counter, assign random turns for each playr
    public Game newEmojiRound(String gameId) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        return currentRoom;
    }

    // Store sent emojis from each player
    public Game emoji(String gameId, Long playerId, EmojiPostDTO emojiPostDTO) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        return currentRoom;
    }

    // Store sent votes from each player, if all players have voted, compute scores
    // for each player
    public Game vote(String gameId, Long playerId, VotePostDTO votePostDTO) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        return currentRoom;
    }

    // Flush buffer for previous round, refresh current round counter
    public Game newRound(String gameId) {
        Game currentRoom = getGameByIdWithPlayers(gameId);
        return currentRoom;
    }

    // check if the user is the host of the game
    public boolean isHost(Long userId, String gameId) {
        Game gameByGameId = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return gameByGameId.getHostId().equals(userId);
    }

    public Game joinRoom(String gameId, Long userId) {
        // Fetch the game along with its players to avoid lazy loading issues.
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        if (game.getHostId().equals(userId)) {
            return game;
        }

        // Check if the user is already in a game.
        playerRepository.findByUserId(userId).ifPresent(p -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player is already in a game");
        });

        // Ensure the game is not full.
        if (game.getPlayers().size() >= 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game is full");
        }

        // Create a new player or get the existing one.
        Player player = getOrCreatePlayerFromUser(userId);
        player.setGame(game);
        player.setHost(false); // Set host status for the

        // Add the player to the game and save it.
        game.getPlayers().add(player);
        playerRepository.save(player); // Save the player to persist the changes
        return gameRepository.save(game); // Save the game to update the player list and return
    }

    public boolean isPlayerInAnyGame(Long userId) {
        // find if the player is already in a game player.getGame().getGameId() returns
        // not null
        return playerRepository.findByUserId(userId).get().getGame() != null;
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
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return game.getPlayers();
    }

    public Player getPlayerById(String gameId, Long playerId) {
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return game.getPlayers().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player not found in game: " + gameId));
    }

    public Player getPlayerByUsername(String gameId, String username) {
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return game.getPlayers().stream()
                .filter(player -> player.getUser().getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player not found in game: " + gameId));
    }

    public Game startGame(String gameId) {
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
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
        Settings settings = game.getSettings();
        List<SongInfo> songs = spotifyService.searchSong(settings.getMarket(), settings.getGenre(),
                settings.getArtist(), token);

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
        SongInfo spySong = songInfoRepository.save(songs.get(0));
        game.getPlayers().get(0).setSongInfo(spySong);

        // Assign the second song to the rest of the players (players 2, 3, and 4)
        SongInfo commonSong = songInfoRepository.save(songs.get(1));
        for (int i = 1; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setSongInfo(commonSong);
            game.getPlayers().get(i).setSpy(false); // Ensure only the first player is marked as the spy
        }

        // Store the updated player information in the repository
        playerRepository.saveAll(game.getPlayers());
    }

    public PlayerSongInfoDTO getCurrentSongInfo(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        return createPlayerSongInfoDTO(player);
    }

    public List<PlayerSongInfoDTO> getSongs(String gameId) {
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        return game.getPlayers().stream()
                .map(this::createPlayerSongInfoDTO)
                .collect(Collectors.toList());
    }

    public Game sortTurnOrder(String gameId) {
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        List<Player> players = game.getPlayers();
        Collections.shuffle(players); // Shuffle the list to randomize turn order

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setTurn(i + 1);
            playerRepository.save(players.get(i));
        }

        game.setPlayers(players); // Set the shuffled list back to the game
        return gameRepository.save(game); // Save the changes to the database
    }

    public void savePlayerEmojis(String gameId, Long playerId, List<String> emojis, int round) {
        Player player = getPlayerById(gameId, playerId);

        // Check if it's player's turn
        if (player.getGame().getCurrentTurn() != player.getTurn()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "It's not this player's turn");
        }

        // Check the number of emojis
        if (emojis.size() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot send more than 5 emojis");
        }

        // Save emojis to player
//        player.setEmojis(emojis);
//        playerRepository.save(player);

        // Save emojis to player based on the round
        if (round == 1) {
            player.setEmojis(emojis);
        } else if (round == 2) {
            player.setEmojis2(emojis);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid round number");
        }
        playerRepository.save(player);


        // Increment current turn
        Game game = player.getGame();
        int nextTurn = game.getCurrentTurn() + 1;
        if (nextTurn > game.getPlayers().size()) {
            nextTurn = 1; // Wrap around if it exceeds the number of players
            game.incrementRound(); // Increment round after all players have played
        }
        game.setCurrentTurn(nextTurn);
        gameRepository.save(game);
    }

    public void vote(String gameId, Long voterId, Long votedPlayerId) {
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        Player votedPlayer = game.getPlayers().stream()
                .filter(player -> player.getId().equals(votedPlayerId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Voted player not found in game: " + gameId));

        if (voterId.equals(votedPlayerId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot vote for yourself");
        }

        // Increment votes
        votedPlayer.setVotes(votedPlayer.getVotes() + 1);
        playerRepository.save(votedPlayer);

        // Increment votedPlayers count
        game.setVotedPlayers(game.getVotedPlayers() + 1);
        gameRepository.save(game);

        // Check if all players have voted
        if (game.getVotedPlayers() == game.getPlayers().size()) {
            determineWinner(game);
            System.out.println(game.getVotedPlayers());
            System.out.println(game.getPlayers().size());
        }
    }

    private void determineWinner(Game game) {
        List<Player> players = game.getPlayers();
        Player spy = players.stream().filter(Player::isSpy).findFirst().orElse(null);

        if (spy != null && spy.getVotes() < 2) {
            // Spy wins
            spy.setScore(spy.getScore() + 3);
            spy.setWinner(true);
            players.forEach(p -> p.setWinner(p.getId().equals(spy.getId())));
        } else {
            // Non-spies win
            players.stream().filter(p -> !p.isSpy()).forEach(player -> {
                player.setScore(player.getScore() + 3);
                player.setWinner(true);
            });
            if (spy != null) {
                spy.setWinner(false);
            }
        }

        // Save all player changes
        players.forEach(playerRepository::save);
        gameRepository.save(game);
    }



//    public Game setCurrentTurn(String gameId) {
//        Game game = gameRepository.findByGameIdWithPlayers(gameId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
//        game.setCurrentTurn(game.getCurrentTurn() + 1);
//        // Reset emojis for all players
//        for (Player player : game.getPlayers()) {
//            player.setEmojis(new ArrayList<>());
//            playerRepository.save(player);
//        }
//        return gameRepository.save(game);
//    }

//    public Game startNewEmojisRound(String gameId) {
//        Game game = gameRepository.findByGameIdWithPlayers(gameId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
//        game.setCurrentEmojiRound(game.getCurrentEmojiRound() + 1);
//        // Reset emojis for all players
//        for (Player player : game.getPlayers()) {
//            player.setEmojis(new ArrayList<>());
//            playerRepository.save(player);
//        }
//        return gameRepository.save(game);
//    }
//
//    public Map<String, List<String>> viewEmojis(Long gameId) {
//        Game game = gameRepository.findById(gameId)
//                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
//
//        List<Player> players = game.getPlayers();
//        Map<String, List<String>> playerEmojis = new HashMap<>();
//
//        for (Player player : players) {
//            playerEmojis.put(player.getUser().getUsername(), player.getEmojis());
//        }
//
//        return playerEmojis;
//    }

//    public Game startVoting(String gameId, Long votedPlayerId) {
//        Game game = gameRepository.findByGameIdWithPlayers(gameId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
//        if (game == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
//        }
//        Player votedPlayer = playerRepository.findById(votedPlayerId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voted player not found"));
//        votedPlayer.setVotes(votedPlayer.getVotes() + 1); // Increment the votes for the voted player
//        playerRepository.save(votedPlayer); // Save the voted player's state
//        return game;
//    }
//
//    public Game endVoting(String gameId) {
//        Game game = gameRepository.findByGameIdWithPlayers(gameId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
//        if (game == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
//        }
//        return game;
//    }
//
//    public Game declareWinner(String gameId) {
//        Game game = gameRepository.findByGameIdWithPlayers(gameId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
//        if (game == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
//        }
//        Player winner = game.getPlayers().stream().max(Comparator.comparing(Player::getVotes)).orElse(null);
//        game.setWinner(winner);
//        return gameRepository.save(game);
//    }
//
//    public Game endRound(String gameId) {
//        Game game = gameRepository.findByGameIdWithPlayers(gameId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
//        if (game == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
//        }
//        // Update scores for all players based on the round results
//        for (Player player : game.getPlayers()) {
//            // Update logic based on your game rules
//            player.setScore(player.getScore() + 1); // Example increment
//        }
//        playerRepository.saveAll(game.getPlayers());
//        return gameRepository.save(game);
//    }

    // Players except host can leave the room by pressing Quit button
    public Game leaveRoom(String gameId, Player player) {
        Game game = gameRepository.findByGameIdWithPlayers(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        List<Player> players = game.getPlayers();
        players.remove(player);
        playerRepository.delete(player);
        game.setPlayers(players);
        return gameRepository.save(game);
    }

}
