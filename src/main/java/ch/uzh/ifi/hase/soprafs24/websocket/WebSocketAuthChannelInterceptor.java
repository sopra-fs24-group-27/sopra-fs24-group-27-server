package ch.uzh.ifi.hase.soprafs24.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String authToken = accessor.getFirstNativeHeader("Authorization");

        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7); // Remove "Bearer " prefix
            // print the processed token
            System.out.println("socket processed token: " + authToken);

            if (userService.validateToken(authToken)) {
                Authentication authentication = userService.getAuthentication(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    
        return message;
    }
    
}
