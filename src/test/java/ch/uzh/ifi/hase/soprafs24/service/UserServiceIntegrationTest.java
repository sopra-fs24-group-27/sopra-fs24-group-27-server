package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class UserServiceIntegrationTest {

  @Qualifier("userRepository")
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
  }

  @Test
  public void createUser_validInputs_success() {

    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setUsername("testUsername");
    testUser.setPassword("password");
    testUser.setBirthDate(null);

    User createdUser = userService.createUser(testUser);

    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.ONLINE, createdUser.getStatus());
    assertNull(createdUser.getBirthDate()); 
  }

  @Test
  public void createUser_duplicateUsername_throwsException() {

    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setUsername("testUsername");
    testUser.setPassword("password");
    testUser.setBirthDate(null);

    User createdUser = userService.createUser(testUser);

    User duplicateUser = new User();
    duplicateUser.setUsername("testUsername");

    assertThrows(IllegalArgumentException.class, () -> userService.createUser(duplicateUser));
  }

  @Test
  public void loginUser_validCredentials_success() {
      
      User testUser = new User();
      testUser.setUsername("testUsername");
      testUser.setPassword("testPassword");
      testUser.setBirthDate(null);
      testUser.setStatus(UserStatus.OFFLINE); 

      userRepository.save(testUser);

      User loggedInUser = userService.loginUser("testUsername", "testPassword");

      assertNotNull(loggedInUser);
      assertEquals(testUser.getId(), loggedInUser.getId());
      assertEquals(testUser.getUsername(), loggedInUser.getUsername());
      assertEquals(UserStatus.ONLINE, loggedInUser.getStatus());
  }

  @Test
  public void logoutUser_validToken_success() {

      User testUser = new User();
      testUser.setUsername("testUsername");
      testUser.setPassword("testPassword");
      testUser.setStatus(UserStatus.ONLINE);

      userRepository.save(testUser);

      userService.logoutUser(testUser.getToken());

      User loggedOutUser = userRepository.findByUsername("testUsername");
      assertNotNull(loggedOutUser);
      assertEquals(UserStatus.OFFLINE, loggedOutUser.getStatus());
  }

  @Test
public void updateUserDetails_validInputs_success() {

    User testUser = new User();
    testUser.setUsername("testUsername");
    testUser.setPassword("password");
    testUser.setBirthDate(null);
    testUser.setStatus(UserStatus.ONLINE);
    userRepository.save(testUser);

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("updatedUsername");
    userPostDTO.setName("updatedName");

    User updatedUser = userService.updateUserDetails(testUser.getId(), userPostDTO);

    assertEquals(userPostDTO.getUsername(), updatedUser.getUsername());
    assertEquals(userPostDTO.getName(), updatedUser.getName());
  }

}
