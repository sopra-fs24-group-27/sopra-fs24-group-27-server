package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import ch.uzh.ifi.hase.soprafs24.entity.Settings;

import java.util.ArrayList;
import java.util.List;

public class GameResponseDTO {
    private String gameId;
    private Long hostId;
    private int currentRound;
    private Settings settings;
    private List<PlayerInfoDTO> players = new ArrayList<>();  // To share player information without spy and song information

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

    public List<PlayerInfoDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInfoDTO> players) {
        this.players = players;
    }

}
