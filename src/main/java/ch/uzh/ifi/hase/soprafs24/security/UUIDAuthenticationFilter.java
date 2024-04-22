package ch.uzh.ifi.hase.soprafs24.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import ch.uzh.ifi.hase.soprafs24.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UUIDAuthenticationFilter extends GenericFilterBean {

    private UserService userService;

    public UUIDAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        System.out.println("Authorization Header: " + token); // Check if token is received correctly
    
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            System.out.println("Processed token: " + token);
    
            if (userService.validateToken(token)) {
                Authentication authentication = userService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authentication set in Security Context: " + authentication);
            } else {
                System.out.println("Token validation failed.");
            }
        }
        chain.doFilter(request, response);
    }
}    
