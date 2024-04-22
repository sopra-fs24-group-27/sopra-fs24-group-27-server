package ch.uzh.ifi.hase.soprafs24.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-games/{gameId}").setAllowedOrigins("*"); // Plain WebSocket endpoint
        registry.addEndpoint("/games/{gameId}").setAllowedOrigins("*").withSockJS();  // Existing SockJS endpoint
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Set prefix for endpoints the client will send messages to
        registry.enableSimpleBroker("/topic");
        // Set prefix for the endpoint that the client subscribes to so that it can
        // receive broadcasts
        registry.setApplicationDestinationPrefixes("/app");

    }
}
