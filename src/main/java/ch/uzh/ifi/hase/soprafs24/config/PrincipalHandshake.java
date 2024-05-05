/*package ch.uzh.ifi.hase.soprafs24.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class PrincipalHandshake extends DefaultHandshakeHandler {
    // custom to store principal
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // Generate UUID for session id
        return new StompPrincipal(UUID.randomUUID().toString());
    }
    
}
*/