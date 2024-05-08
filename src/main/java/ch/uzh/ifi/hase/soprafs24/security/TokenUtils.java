package ch.uzh.ifi.hase.soprafs24.security;

import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
// import java.util.UUID;

@Component
public class TokenUtils {

    @Autowired
    private UserRepository userRepository;

    public String generate() {
        return "02fdd2bd-1669-48b9-b51b-1e724f97688f";
        // return UUID.randomUUID().toString();
    }

    public boolean isEmpty(String token) {
        return (token == null || token.equals(""));
    }

    public boolean isValid(String token) {
        // public boolean isValid(String token, Long userId) {
        if (isEmpty(token)) {
            return false;
        }
        Optional<User> user = userRepository.findByToken(token);
        return user.isPresent();
        // if (!user.isPresent()) {
        // return false;
        // }
        // return (user.get().getId() == userId);
    }
}
