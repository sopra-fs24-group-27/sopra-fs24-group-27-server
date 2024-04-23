package ch.uzh.ifi.hase.soprafs24.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/games/{gameId}").setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new WebSocketAuthChannelInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
    
    // Listen for connect events
    @Bean
    public ApplicationListener<SessionConnectEvent> handleConnectListener() {
        return event -> {
            String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
            System.out.println("WebSocket connection established with sessionId: " + sessionId);
        };
    }
    
    // Listen for disconnect events
    @Bean
    public ApplicationListener<SessionDisconnectEvent> handleDisconnectListener() {
        return event -> {
            String sessionId = event.getSessionId();
            System.out.println("WebSocket connection closed with sessionId: " + sessionId);
        };
    }
}
