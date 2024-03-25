package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    private String hostId;

    private int currentRound;

    @Embedded
    private Settings settings;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();

    private int currentEmojiRound;

    private int currentTurn;

    private int votedPlayers;

    private String winner; // Could be an enum or a Player reference

    // Getters and Setters
    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
    public String getHostId() { return hostId; }
    public void setHostId(String hostId) { this.hostId = hostId; }
    public int getCurrentRound() { return currentRound; }
    public void setCurrentRound(int currentRound) { this.currentRound = currentRound; }
    public Settings getSettings() { return settings; }
    public void setSettings(Settings settings) { this.settings = settings; }
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }
    public int getCurrentEmojiRound() { return currentEmojiRound; }
    public void setCurrentEmojiRound(int currentEmojiRound) { this.currentEmojiRound = currentEmojiRound; }
    public int getCurrentTurn() { return currentTurn; }
    public void setCurrentTurn(int currentTurn) { this.currentTurn = currentTurn; }
    public int getVotedPlayers() { return votedPlayers; }
    public void setVotedPlayers(int votedPlayers) { this.votedPlayers = votedPlayers; }
    public String getWinner() { return winner; }
    public void setWinner(String winner) { this.winner = winner; }
}
