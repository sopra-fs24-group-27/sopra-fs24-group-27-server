package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.io.Serializable;

// We set the relation betwwen "Player" and "User" to "Association", to seperate the game related characteristics from the user related characteristics.

@Entity
@Table(name = "PLAYER")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure ID is auto-generated
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // many players can belong to one user
    @JoinColumn(name = "user_id") // This column links Player to a specific User
    private User user;

    @Column
    private boolean isHost; // True if the player is the host, false otherwise.

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // Prevents the serialization of game back to JSON which stops the recursion
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
    
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
