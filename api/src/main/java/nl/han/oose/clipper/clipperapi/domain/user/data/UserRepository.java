package nl.han.oose.clipper.clipperapi.domain.user.data;

import nl.han.oose.clipper.clipperapi.domain.user.application.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<nl.han.oose.clipper.clipperapi.domain.user.application.dto.User, Long> {

  @Query("SELECT u FROM User u WHERE u.email = ?1")
  Optional<User> findUserByEmail(String email);

}
