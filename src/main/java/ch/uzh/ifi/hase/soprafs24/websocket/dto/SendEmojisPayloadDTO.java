package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import java.util.List;

public class SendEmojisPayloadDTO {
    private Long id;
    private List<String> emojis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }
}
