package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs24.repository.PlayerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createRoom(Game newRoom) {
        newRoom.setCurrentRound(1);
        Player host = playerRepository.findById(newRoom.getHostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
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

    public Game startRound(String gameId) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        // Assign player roles
        return gameByGameId;
    }

    public Game sortTurnOrder(String gameId) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        // Sort player turn order for sending emojis
        return gameByGameId;
    }

    public Game sendEmojis(String gameId, Player player) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        // Update the right player's emojis sent
        return gameByGameId;
    }

    public Game vote(String gameId, Player player) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        // Update player votes
        return gameByGameId;
    }

    public Game endVoting(String gameId) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        // Display player votes
        return gameByGameId;
    }

    public Game declareWinner(String gameId) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        // Delare winner
        return gameByGameId;
    }

    public Game endRound(String gameId) {
        Game gameByGameId = gameRepository.findByGameId(gameId);
        // Update player scores
        return gameByGameId;
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
