package nl.han.oose.clipper.clipperapi.domain.user.application;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserRequest;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserResponse;
import nl.han.oose.clipper.clipperapi.domain.user.business.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

  private final IUserService userService;

  @Autowired
  public UserResource(nl.han.oose.clipper.clipperapi.domain.user.business.IUserService userService) {
    this.userService = userService;
  }

  @CrossOrigin
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @CrossOrigin
  @PostMapping("/authenticate")
  public ResponseEntity<UserResponse> validateUserCredentials(@RequestBody UserRequest userRequest) {
    return userService.validateUser(userRequest);
  }
}
