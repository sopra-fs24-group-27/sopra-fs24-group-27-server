package ch.uzh.ifi.hase.soprafs24.rest.dto;

import java.util.List;

// This DTO object contains the following information for a player:
// id, username, avatar, score, emojis, turn
// These information will be sent to all players in the game

public class PlayerInfoDTO {
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private int score;
    private List<String> emojis;
    private int turn;

    public PlayerInfoDTO() {
        // Default constructor
    }

    public PlayerInfoDTO(Long id, Long userId, String username, String avatar, int score, List<String> emojis,
            int turn) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.score = score;
        this.emojis = emojis;
        this.turn = turn;
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
