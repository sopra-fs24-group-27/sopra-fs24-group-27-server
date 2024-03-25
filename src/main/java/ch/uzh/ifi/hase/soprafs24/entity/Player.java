package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLAYER")
public class Player extends User {
    
    @Column
    private String avatar;
    
    @Column
    private int score;
    
    @Column
    private int turn;
    
    @ElementCollection // This annotation is used for storing a list of basic elements or embeddable objects
    private List<String> emojis; // Stores multiple emojis
    
    @Column
    private int votes;
    
    // Constructor, getters, and setters
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getTurn() { return turn; }
    public void setTurn(int turn) { this.turn = turn; }
    public List<String> getEmojis() { return emojis; }
    public void setEmojis(List<String> emojis) { this.emojis = emojis; }
    public int getVotes() { return votes; }
    public void setVotes(int votes) { this.votes = votes; }
}
