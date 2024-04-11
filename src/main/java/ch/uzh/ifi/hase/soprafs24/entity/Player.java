package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLAYER")
public class Player extends User {

    @Column
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game; // Association to Game

    @ManyToOne(fetch = FetchType.LAZY)
    private SongInfo songInfo; // Song assigned to the player, different players can have the same song

    @Column
    private boolean isSpy; // True if the player is the spy, false otherwise.

    @Column
    private int score;

    @Column
    private int turn;

    @ElementCollection // This annotation is used for storing a list of basic elements or embeddable
                       // objects
    private List<String> emojis; // Stores multiple emojis

    @Column
    private int votes;

    // Constructor, getters, and setters
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    // Constructor, getters, and setters for new fields
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isSpy() {
        return isSpy;
    }

    public void setSpy(boolean spy) {
        this.isSpy = spy;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
