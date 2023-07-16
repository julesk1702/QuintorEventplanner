package nl.han.oose.clipper.clipperapi.domain.user.business.impl;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.User;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserRequest;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.UserResponse;
import nl.han.oose.clipper.clipperapi.domain.user.business.IUserService;
import nl.han.oose.clipper.clipperapi.domain.user.data.IUserDAO;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import nl.han.oose.clipper.clipperapi.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  private final IUserDAO userDAO;

  @Autowired
  public UserServiceImpl(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public ResponseEntity<UserResponse> validateUser(UserRequest userRequest) {
    if (userRequest.getEmail() == null || userRequest.getPassword() == null)
      throw new IllegalArgumentException("E-mail or password cannot be null");
    User userInfo = userDAO.getUserByEmail(userRequest.getEmail());
    if (userInfo == null || !userInfo.getPassword().equals(userRequest.getPassword()))
      throw new UnauthorizedException("Invalid Credentials");
    return ResponseEntity.ok(new UserResponse(userInfo.getUserId(), userInfo.getEmail(), userInfo.getRole()));
  }

  @Override
  public ResponseEntity<UserResponse> getUserById(Long id) {
    User userInfo = userDAO.getUserById(id);
    if (userInfo == null) throw new NotFoundException("User not found");
    return ResponseEntity.ok(new UserResponse(userInfo.getUserId(), userInfo.getEmail(), userInfo.getRole()));
  }
}
