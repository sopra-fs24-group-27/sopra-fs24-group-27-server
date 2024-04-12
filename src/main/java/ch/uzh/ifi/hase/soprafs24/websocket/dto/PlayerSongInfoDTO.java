package ch.uzh.ifi.hase.soprafs24.websocket.dto;

// This DTO object contains the following information for a player:
// id, isSpy, songTitle, songArtist, playUrl, imageUrl
// This information will only be sent to the corresponding player, not all players in the game

public class PlayerSongInfoDTO {
    private Long id;
    private Boolean isSpy;
    private String songTitle;
    private String songArtist;
    private String playUrl;
    private String imageUrl;

    // Default constructor
    public PlayerSongInfoDTO() {
    }

    // Constructor
    public PlayerSongInfoDTO(Long id, Boolean isSpy, String songTitle, String songArtist, String playUrl, String imageUrl) {
        this.id = id;
        this.isSpy = isSpy;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.playUrl = playUrl;
        this.imageUrl = imageUrl;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Boolean getSpy() {
        return isSpy;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setSpy(Boolean spy) {
        isSpy = spy;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
