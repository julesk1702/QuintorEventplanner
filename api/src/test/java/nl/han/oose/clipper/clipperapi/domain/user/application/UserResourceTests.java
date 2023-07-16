package nl.han.oose.clipper.clipperapi.domain.user.application;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserRequest;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserResponse;
import nl.han.oose.clipper.clipperapi.domain.user.business.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTests {
  
  @Mock
  private IUserService userService;

  @InjectMocks
  private UserResource userResource;

  @Test
  void testGetUserById() {
    Long userId = 123L;
    UserResponse expectedResponse = new UserResponse();

    when(userService.getUserById(userId)).thenReturn(ResponseEntity.ok(expectedResponse));

    ResponseEntity<UserResponse> response = userResource.getUserById(userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void testValidateUserCredentials() {
    UserRequest userRequest = new UserRequest();
    UserResponse expectedResponse = new UserResponse();

    when(userService.validateUser(userRequest)).thenReturn(ResponseEntity.ok(expectedResponse));

    ResponseEntity<UserResponse> response = userResource.validateUserCredentials(userRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }


}
