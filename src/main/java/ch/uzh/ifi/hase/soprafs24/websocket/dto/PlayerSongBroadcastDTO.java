package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import java.util.List;

public class PlayerSongBroadcastDTO {
    private Long id; // player ID
    private Long userId;
    private String username;
    private String avatar;
    private int score;
    private List<String> emojis;
    private int turn;
    private String songTitle;
    private String songArtist;
    private String imageUrl;
    private String playUrl;

    public PlayerSongBroadcastDTO() {
        // Default constructor
    }

    public PlayerSongBroadcastDTO(Long id, Long userId, String username, String avatar, int score, List<String> emojis,
            int turn, String songTitle, String songArtist, String imageUrl, String playUrl) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.score = score;
        this.emojis = emojis;
        this.turn = turn;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.imageUrl = imageUrl;
        this.playUrl = playUrl;
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

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

}
