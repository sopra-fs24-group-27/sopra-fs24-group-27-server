package ch.uzh.ifi.hase.soprafs24.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .simpDestMatchers("/app/**").authenticated()  // Ensure all messages sent to /app/** require authentication
            .anyMessage().authenticated();  // All other messages also require authentication
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true; // Disable CSRF for WebSockets
    }
}
