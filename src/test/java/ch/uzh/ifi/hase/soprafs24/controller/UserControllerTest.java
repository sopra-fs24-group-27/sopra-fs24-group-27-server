package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import static org.hamcrest.Matchers.*;

@WebMvcTest(value = UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private PasswordEncoder passwordEncoder;

  // Helper method to convert objects to JSON strings
  private String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException("Failed to convert object to JSON string", e);
    }
  }

  // Test method for GET /users endpoint
  @Test
  public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
      User user = new User();
      user.setUsername("username");
      user.setBirthDate(java.sql.Date.valueOf(LocalDate.now()));

      List<User> allUsers = Collections.singletonList(user);
      given(userService.getUsers()).willReturn(allUsers);

      mockMvc.perform(get("/users")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].username", is(user.getUsername())));
  }

  // Test method for POST /register endpoint
  @Test
  public void createUser_validInput_userCreated() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setUsername("testUsername");
    user.setPassword("testPassword");

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("testUsername");
    userPostDTO.setPassword("testPassword");

    given(userService.createUser(Mockito.any())).willReturn(user);

    MockHttpServletRequestBuilder postRequest = post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO));

    mockMvc.perform(postRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(user.getId().intValue())))
        .andExpect(jsonPath("$.username", is(user.getUsername())));
  }

  // New test method for GET /users/{userId} endpoint
  @Test
  public void getUserById_existingUser_userFound() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setUsername("existingUser");

    given(userService.getUserById(1L)).willReturn(user);

    mockMvc.perform(get("/users/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is("existingUser")));
  }

  // New test method for PUT /users/{userId} endpoint
  @Test
  public void updateUserDetails() throws Exception {
    Long userId = 1L;
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("UpdatedUsername");

    User updatedUser = new User();
    updatedUser.setId(userId);
    updatedUser.setUsername("UpdatedUsername");

    given(userService.updateUserDetails(userId, userPostDTO)).willReturn(updatedUser);

    mockMvc.perform(put("/users/{userId}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO)))
        .andExpect(status().isOk());
  }

  // Test method for POST /login endpoint
  @Test
  public void testLogin() throws Exception {
    UserPostDTO requestDTO = new UserPostDTO();
    requestDTO.setUsername("testUser");
    requestDTO.setPassword("testPassword");

    when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

    User user = new User();
    user.setId(1L);
    user.setUsername("testUser");

    given(userService.loginUser("testUser", "testPassword")).willReturn(user);


    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(user.getId().intValue()))
            .andExpect(jsonPath("$.username").value(user.getUsername()));
  }

  // Test method for POST /logout endpoint
  @Test
  public void testLogout() throws Exception {
    String token = "testToken";

    User user = new User();
    user.setId(1L);
    user.setStatus(UserStatus.ONLINE);

    when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

    doNothing().when(userService).logoutUser(token);

    mockMvc.perform(post("/logout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(token))
            .andExpect(status().isOk());
  }
}
