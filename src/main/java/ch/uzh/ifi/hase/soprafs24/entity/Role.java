package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.constant.RoleTitle;

public class Role {
    private RoleTitle title;
    private SongInfo songInfo;

    // Constructor
    public Role(RoleTitle title, SongInfo songInfo) {
        this.title = title;
        this.songInfo = songInfo;
    }

    // Getters and Setters
    public RoleTitle getTitle() {
        return title;
    }

    public void setTitle(RoleTitle title) {
        this.title = title;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }
}
