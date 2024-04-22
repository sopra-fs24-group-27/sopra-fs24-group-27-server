package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }
  public void logoutUser(String token) {
    Optional<User> optionalUser = userRepository.findByToken(token);

    if (optionalUser.isPresent()) {
        User user = optionalUser.get(); 
        user.setStatus(UserStatus.OFFLINE); 
        userRepository.save(user); 
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
}

  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    newUser.setStatus(UserStatus.ONLINE);
    newUser.setBirthDate(null);
    checkIfUserExists(newUser);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  /**
   * This is a helper method that will check the uniqueness criteria of the username defined in the User entity. The method will do nothing if the input is unique and throw an error otherwise.

   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   */
  private void checkIfUserExists(User userToBeCreated) {
    // check if username is already taken
    User checkUsername = userRepository.findByUsername(userToBeCreated.getUsername());
    if (checkUsername != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken, please choose another one");
    }
  }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return user;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User getUserByToken(String token) {
        return userRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User updateUserDetails(long userId, @RequestBody UserPostDTO userPostDTO) {

      User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
      System.out.println("Received name: " + userPostDTO.getName()); // 输出接收到的name值
      user.setBirthDate(userPostDTO.getBirthDate());

      user.setUsername(userPostDTO.getUsername());
      
      user.setName(userPostDTO.getName());
      // Save the updated user to the database
      userRepository.save(user);
      System.out.println("After update: " + user.getName());  
      return user;
  }

    public boolean validateToken(String replace) {
        return userRepository.findByToken(replace).isPresent();
    }
    
    public Authentication getAuthentication(String token) {
      User user = userRepository.findByToken(token)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

      // Directly return an authentication token with minimal authorities
      return new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());

      // the returned authentication object will be set in the SecurityContextHolder
      //UsernamePasswordAuthenticationToken {
     //Principal: "username_of_the_user", // String
     //Credentials: null, // Since it's not used post-authentication
     //Authorities: [] // Empty list, no roles assigned

  }
}

