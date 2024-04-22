package ch.uzh.ifi.hase.soprafs24.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import ch.uzh.ifi.hase.soprafs24.security.UUIDAuthenticationFilter;
import ch.uzh.ifi.hase.soprafs24.service.UserService;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class WebSocketSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public WebSocketSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UUIDAuthenticationFilter uuidAuthenticationFilter = new UUIDAuthenticationFilter(userService);
        http.addFilterBefore(uuidAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow CORS preflight (OPTIONS) for all endpoints
            .antMatchers("/register", "/login").permitAll()
            .antMatchers(HttpMethod.GET, "/users").authenticated() // Ensure only authenticated users can access
            .antMatchers(HttpMethod.POST, "/games").authenticated() // Ensure only authenticated users can access
            .antMatchers("/games/**").authenticated() // Ensure only authenticated users can access
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new UUIDAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class)
            .csrf().disable();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Adjust as necessary
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true); // This allows sending cookies and specific headers
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
