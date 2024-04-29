package ch.uzh.ifi.hase.soprafs24.rest.dto;

import java.util.List;

public class EmojiPostDTO {

    private List<String> emojis;

    public List<String> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }
}