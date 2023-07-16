package nl.han.oose.clipper.clipperapi.domain.event.data;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  @Modifying
  @Query("DELETE FROM Registration r WHERE r.event_id = ?1")
  void deleteEventRegistrationById(Long id);
  @Modifying
  @Query("DELETE FROM Registration r WHERE r.event_id = ?1 AND r.user_id = ?2")
  void unRegisterUser(Long id, Long userId);
  @Query("SELECT r FROM Registration r WHERE r.event_id = ?1 AND r.user_id = ?2")
  List<Registration> checkIfUserIsRegistered(Long eventId, Long userId);
  @Query(value="SELECT email FROM users u INNER JOIN registrations r ON u.user_id = r.user_id WHERE r.event_id = ?1", nativeQuery = true)
  List<String> getRegistrationsByEventId(Long id);

  @Query(value="select u.user_id, u.email, r.note, ucd.customDiets from registrations r join users u on r.user_id = u.user_id left outer join user_custom_diets ucd on r.user_id = ucd.user_id where r.event_id = ?1", nativeQuery = true)
  List<Object[]> getRegistrationsWithCustomDietsByEventId(Long id);

}
