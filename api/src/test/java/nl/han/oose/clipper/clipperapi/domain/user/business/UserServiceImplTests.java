package nl.han.oose.clipper.clipperapi.domain.user.business;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.user.business.impl.UserServiceImpl;
import nl.han.oose.clipper.clipperapi.domain.user.data.IUserDAO;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import nl.han.oose.clipper.clipperapi.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTests {
  
  @Mock
  private IUserDAO userDAO;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void testValidateUserValidCredentials() {
    UserRequest userRequest = new UserRequest();
    userRequest.setEmail("test@example.com");
    userRequest.setPassword("password");

    User userInfo = new User();
    userInfo.setUserId(123L);
    userInfo.setEmail("test@example.com");
    userInfo.setPassword("password");
    userInfo.setRole("user");

    when(userDAO.getUserByEmail(userRequest.getEmail())).thenReturn(userInfo);

    ResponseEntity<UserResponse> response = userService.validateUser(userRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userInfo.getUserId(), response.getBody().getUserId());
    assertEquals(userInfo.getEmail(), response.getBody().getEmail());
    assertEquals(userInfo.getRole(), response.getBody().getRole());
  }

  @Test
  void testValidateUserInvalidCredentials() {
    UserRequest userRequest = new UserRequest();
    userRequest.setEmail("test@example.com");
    userRequest.setPassword("wrong_password");

    when(userDAO.getUserByEmail(userRequest.getEmail())).thenReturn(null);

    UnauthorizedException exception = assertThrows(
        UnauthorizedException.class,
        () -> userService.validateUser(userRequest)
    );

    assertEquals("Invalid Credentials", exception.getMessage());
  }

  @Test
  void testValidateUserNullEmailAndPassword() {
    UserRequest userRequest = new UserRequest();

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> userService.validateUser(userRequest)
    );

    assertEquals("E-mail or password cannot be null", exception.getMessage());
  }

  @Test
  void testGetUserById() {
    Long userId = 123L;

    User userInfo = new User();
    userInfo.setUserId(userId);
    userInfo.setEmail("test@example.com");
    userInfo.setRole("user");

    when(userDAO.getUserById(userId)).thenReturn(userInfo);

    ResponseEntity<UserResponse> response = userService.getUserById(userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userInfo.getUserId(), response.getBody().getUserId());
    assertEquals(userInfo.getEmail(), response.getBody().getEmail());
    assertEquals(userInfo.getRole(), response.getBody().getRole());
  }

  @Test
  void testGetUserByIdNotFound() {
    Long userId = 123L;

    when(userDAO.getUserById(userId)).thenReturn(null);

    NotFoundException exception = assertThrows(
        NotFoundException.class,
        () -> userService.getUserById(userId)
    );

    assertEquals("User not found", exception.getMessage());
  }


}
