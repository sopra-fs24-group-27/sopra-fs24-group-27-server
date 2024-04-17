package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserGetDTO> getAllUsers() {
    // fetch all users in the internal representation
    List<User> users = userService.getUsers();
    List<UserGetDTO> userGetDTOs = new ArrayList<>();

    // convert each user to the API representation
    for (User user : users) {
      userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
    }
    return userGetDTOs;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
    // convert API user to internal representation
    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // create user
    User createdUser = userService.createUser(userInput);
    // convert internal representation of user back to API
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
    if (userPostDTO.getUsername() == null || userPostDTO.getPassword() == null ||
        userPostDTO.getUsername().isEmpty() || userPostDTO.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Invalid login request: username and password are required.");
    }

    // Call methods in UserService to verify user credentials
    User loginUser = userService.loginUser(userPostDTO.getUsername(), userPostDTO.getPassword());

    // Convert user information to API representation and back
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
  }

  @GetMapping("/token")
  @ResponseBody
  public UserGetDTO getIdByToken(@RequestBody UserPostDTO userPostDTO){
    String token = userPostDTO.getToken();
    User user = userService.getUserByToken(token);
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
  }

  @PostMapping("/logout")
  @ResponseBody
  public void logOut(@RequestBody UserPostDTO userPostDTO){
    String token = userPostDTO.getToken();
    userService.logoutUser(token);
  }

  @GetMapping("/users/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO getUserById(@PathVariable Long userId) {
    // fetch user by id
    User user = userService.getUserById(userId);
    // convert user to API representation
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
  }

  @PutMapping("/users/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO updateUserDetails(@PathVariable Long userId, @RequestBody UserPostDTO userPostDTO) {
      // Update user details
      User updatedUser = userService.updateUserDetails(userId, userPostDTO);
  
      // Convert updated user to API representation
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(updatedUser);
  }
  
}
