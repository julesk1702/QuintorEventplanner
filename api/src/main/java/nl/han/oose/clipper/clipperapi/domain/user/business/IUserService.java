package nl.han.oose.clipper.clipperapi.domain.user.business;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserRequest;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService {

  /**
   * Validates if the user's email and password are correct
   *
   * @param userRequest The user's email and password in a {@link UserRequest}
   * @return {@link ResponseEntity<UserResponse>} with the status code and body (the user's id, email and role)
   */
  ResponseEntity<UserResponse> validateUser(UserRequest userRequest);

  /**
   * Gets the user by email
   *
   * @param id The user's id
   * @return {@link ResponseEntity<UserResponse>} with the status code and body (the user's id, email and role)
   */
  ResponseEntity<UserResponse> getUserById(Long id);

}
