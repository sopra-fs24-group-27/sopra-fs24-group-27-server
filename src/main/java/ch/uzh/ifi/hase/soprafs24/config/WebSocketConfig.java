package ch.uzh.ifi.hase.soprafs24.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Change from "*" to specific allowed origins
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000") // Adjust to match your client's URL
                .withSockJS(); // Register the "/ws" endpoint, enabling SockJS fallback options.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Memory-based message broker to carry the messages back to the client on destinations prefixed with "/topic"
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messages from the client to the server
    }
}
