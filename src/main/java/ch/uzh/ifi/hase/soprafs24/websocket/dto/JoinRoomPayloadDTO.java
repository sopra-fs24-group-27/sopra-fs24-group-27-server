package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class JoinRoomPayloadDTO {
    private Long userId; // Renamed from 'id' to 'userId' to clarify its purpose

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
