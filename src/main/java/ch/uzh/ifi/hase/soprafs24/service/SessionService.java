/*package ch.uzh.ifi.hase.soprafs24.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final Map<String, Long> sessionPlayerMap = new ConcurrentHashMap<>();

    public void registerPlayerSession(String sessionId, Long playerId) {
        sessionPlayerMap.put(sessionId, playerId); 
    }

    public Long getPlayerId(String sessionId) {
        return sessionPlayerMap.get(sessionId);
    }

    public void removePlayerSession(String sessionId) {
        sessionPlayerMap.remove(sessionId);
    }
}
*/