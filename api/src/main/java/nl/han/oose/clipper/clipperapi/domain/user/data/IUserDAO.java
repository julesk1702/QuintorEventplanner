package nl.han.oose.clipper.clipperapi.domain.user.data;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.User;

public interface IUserDAO {

  /**
   * Gets the user from the database with the given email
   *
   * @param email The email of the user to get
   * @return {@link User} with the user's email, password and role
   */
  User getUserByEmail(String email);

  /**
   * Gets the user from the database with the given id
   *
   * @param id The id of the user to get
   * @return {@link User} with the user's email, password and role
   */
  User getUserById(Long id);

}
