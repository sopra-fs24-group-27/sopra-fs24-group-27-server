package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class ModifySettingsPayloadDTO {
    private String language;

    private String artist;

    private String style;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
