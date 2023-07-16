package nl.han.oose.clipper.clipperapi.domain.user.data;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.User;
import nl.han.oose.clipper.clipperapi.domain.user.data.impl.UserDAO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserDAOTests {

  private final String TEST_EMAIL = "test@email.nl";

  @Test
  void testGetUserWithExistingEmail() {
    // Arrange
    UserRepository userRepository = mock(UserRepository.class);
    IUserDAO IUserDAO = new UserDAO(userRepository);

    User userToReturn = new User();
    userToReturn.setEmail(TEST_EMAIL);
    doReturn(Optional.of(userToReturn)).when(userRepository).findUserByEmail(TEST_EMAIL);

    // Act
    User response = IUserDAO.getUserByEmail(TEST_EMAIL);

    // Assert
    verify(userRepository, times(1)).findUserByEmail(TEST_EMAIL);
    assertEquals(TEST_EMAIL, response.getEmail());
  }

  @Test
  void testGetUserWithNonExistingEmail() {
    // Arrange
    UserRepository userRepository = mock(UserRepository.class);
    IUserDAO IUserDAO = new UserDAO(userRepository);

    doReturn(Optional.empty()).when(userRepository).findUserByEmail(TEST_EMAIL);

    // Act
    User response = IUserDAO.getUserByEmail(TEST_EMAIL);

    // Assert
    verify(userRepository, times(1)).findUserByEmail(TEST_EMAIL);
    assertNull(response);
  }

  @Test
  void testGetUserWithExistingId() {
    // Arrange
    UserRepository userRepository = mock(UserRepository.class);
    IUserDAO IUserDAO = new UserDAO(userRepository);

    User userToReturn = new User();
    userToReturn.setUserId(1L);
    doReturn(Optional.of(userToReturn)).when(userRepository).findById(1L);

    // Act
    User response = IUserDAO.getUserById(1L);

    // Assert
    verify(userRepository, times(1)).findById(1L);
    assertEquals(1L, response.getUserId());
  }

  @Test
  void testGetUserWithNonExistingId() {
    // Arrange
    UserRepository userRepository = mock(UserRepository.class);
    IUserDAO IUserDAO = new UserDAO(userRepository);

    doReturn(Optional.empty()).when(userRepository).findById(1L);

    // Act
    User response = IUserDAO.getUserById(1L);

    // Assert
    verify(userRepository, times(1)).findById(1L);
    assertNull(response);
  }
}
