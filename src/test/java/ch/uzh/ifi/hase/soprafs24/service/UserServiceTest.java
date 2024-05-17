package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;

import java.time.LocalDate;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    testUser = new User();
    testUser.setId(1L);
    testUser.setBirthDate(null);
    testUser.setUsername("testUsername");

    Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
  }

  @Test
  public void createUser_validInputs_success() {
    testUser.setPassword("testPassword");

    User createdUser = userService.createUser(testUser);

    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getAvatar());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.ONLINE, createdUser.getStatus());
    // try {
    // User createdUser = userService.createUser(testUser);

    // Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    // assertEquals(testUser.getId(), createdUser.getId());
    // assertEquals(testUser.getUsername(), createdUser.getUsername());
    // assertNotNull(createdUser.getAvatar());
    // assertNotNull(createdUser.getToken());
    // assertEquals(UserStatus.ONLINE, createdUser.getStatus());
    // } catch (Exception e) {
    // fail("Unexpected exception occurred: " + e.getMessage());
    // }
  }

  @Test
  public void createUser_duplicateInputs_throwsException() {

    testUser.setPassword("testPassword");
    userService.createUser(testUser);

    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    try {
      userService.createUser(testUser);
      fail("Expected IllegalArgumentException was not thrown");
    } catch (ResponseStatusException e) {
      assertTrue(e.getMessage().contains("Username is already taken, please choose another one"));
    }
  }

  @Test
  public void loginUser_validCredentials_success() {

    Mockito.when(userRepository.findByUsername("testUser")).thenReturn(testUser);

    testUser.setPassword("testPassword");

    User loggedInUser = userService.loginUser("testUser", "testPassword");
    assertNotNull(loggedInUser);
    assertEquals(testUser.getId(), loggedInUser.getId());
    assertEquals(testUser.getUsername(), loggedInUser.getUsername());
  }

  @Test
  public void loginUser_invalidCredentials_throwsException() {

    Mockito.when(userRepository.findByUsername("testUser")).thenReturn(null);

    assertThrows(ResponseStatusException.class, () -> userService.loginUser("testUser", "wrongPassword"));
  }

  @Test
  public void logoutUser_validToken_success() {

    Mockito.when(userRepository.findByToken(anyString())).thenReturn(Optional.of(testUser));

    userService.logoutUser("testToken");
    assertEquals(UserStatus.OFFLINE, testUser.getStatus());
  }

  @Test
  public void logoutUser_invalidToken_throwsException() {
    Mockito.when(userRepository.findByToken(anyString())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> userService.logoutUser("invalidToken"));
  }

  @Test
  public void updateUserDetails_validInputs_success() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("updatedUsername");
    userPostDTO.setName("updatedName");

    User updatedUser = userService.updateUserDetails(testUser.getId(), userPostDTO);
    Mockito.verify(userRepository, Mockito.times(1)).findById(testUser.getId());
    Mockito.verify(userRepository, Mockito.times(1)).save(testUser);

    assertEquals(userPostDTO.getUsername(), updatedUser.getUsername());
    assertEquals(userPostDTO.getName(), updatedUser.getName());

  }

  @Test
  public void updateUserDetails_invalidUserId_throwsException() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("updatedUsername");
    userPostDTO.setName("updatedName");

    assertThrows(ResponseStatusException.class, () -> userService.updateUserDetails(999L, userPostDTO));
    Mockito.verify(userRepository, Mockito.times(1)).findById(999L);

  }

}
