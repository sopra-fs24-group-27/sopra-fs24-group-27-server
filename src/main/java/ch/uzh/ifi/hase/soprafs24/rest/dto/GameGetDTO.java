package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameGetDTO {
    private String gameId;
    private Long hostId;
    private int currentRound;
    private Settings settings;
    private int currentTurn;

    private int votedPlayers;
    private List<Player> players = new ArrayList<>();

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
}
