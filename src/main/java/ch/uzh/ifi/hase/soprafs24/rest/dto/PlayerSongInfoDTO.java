package ch.uzh.ifi.hase.soprafs24.rest.dto;

public class PlayerSongInfoDTO {
    private Long id;
    private Boolean isSpy;
    private String songTitle;
    private String songArtist;
    private String imageUrl;
    private String playUrl;
    
    public PlayerSongInfoDTO() {}

    public PlayerSongInfoDTO(Long id, Boolean isSpy, String songTitle, String songArtist, String imageUrl, String playUrl) {
        this.id = id;
        this.isSpy = isSpy;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.imageUrl = imageUrl;
        this.playUrl = playUrl;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
