package nl.han.oose.clipper.clipperapi.domain.user.data.impl;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.User;
import nl.han.oose.clipper.clipperapi.domain.user.data.IUserDAO;
import nl.han.oose.clipper.clipperapi.domain.user.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO implements IUserDAO {

  private final UserRepository userRepository;

  @Autowired
  public UserDAO(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findUserByEmail(email).orElse(null);
  }

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

}
