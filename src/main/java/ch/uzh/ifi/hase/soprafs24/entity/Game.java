package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure ID is auto-generated
    private Long id;

    @Column(nullable = false, unique = true)
    private String gameId; // Could be a UUID and be generated by the client

    private Long hostId;

    private int currentRound;

    @Embedded
    private Settings settings;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();

    private int currentEmojiRound;

    private int currentTurn = 1;

    private int votedPlayers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player winner; // Could be a Player reference or a Player object

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentEmojiRound() {
        return currentEmojiRound;
    }

    public void setCurrentEmojiRound(int currentEmojiRound) {
        this.currentEmojiRound = currentEmojiRound;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getVotedPlayers() {
        return votedPlayers;
    }

    public void setVotedPlayers(int votedPlayers) {
        this.votedPlayers = votedPlayers;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void incrementRound() {
        this.currentRound++;
    }
}
