package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.security.TokenUtils;
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
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.Random;

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
  private final TokenUtils tokenUtils; 

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository, TokenUtils tokenUtils) {
    this.userRepository = userRepository;
    this.tokenUtils = tokenUtils;
  }


  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public void logoutUser(String token) {
    checkIfUserWithTokenExists(token);
    User user = userRepository.findByToken(token).get();
    user.setStatus(UserStatus.OFFLINE);
    user.setToken(null);
    userRepository.save(user);
  }

  public User createUser(User newUser) {
    checkIfUserExists(newUser);
    checkIfPasswordIsEmpty(newUser.getPassword());
    Random random = new Random();
    int randomNumber = random.nextInt(27) + 1;
    String avatar = "https://cdn.jsdelivr.net/gh/alohe/avatars/png/vibrent_" + randomNumber + ".png";
    newUser.setAvatar(avatar);
    newUser.setToken(tokenUtils.generate());
    newUser.setStatus(UserStatus.ONLINE);
    newUser.setBirthDate(null);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();
    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username defined in the User entity. The method will do nothing if the input
   * is unique and throw an error otherwise.
   * 
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

  private void checkIfPasswordIsEmpty(String password) {
    if (password == null || password.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Password cannot be empty");
    }
  }

  // Just for test
  private void checkIfUserWithTokenExists(String token) {
    if (!userRepository.findByToken(token).isPresent()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
  }

  public User loginUser(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null || !user.getPassword().equals(password)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
    user.setStatus(UserStatus.ONLINE);
    userRepository.save(user);
    return user;
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  public User updateUserDetails(long userId, @RequestBody UserPostDTO userPostDTO) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    System.out.println("Updating user details: " + userPostDTO);

    user.setBirthDate(userPostDTO.getBirthDate());

    user.setUsername(userPostDTO.getUsername());

    user.setName(userPostDTO.getName());

    user.setAvatar(userPostDTO.getAvatar());

    // Save the updated user to the database
    userRepository.save(user);

    return user;
  }
}