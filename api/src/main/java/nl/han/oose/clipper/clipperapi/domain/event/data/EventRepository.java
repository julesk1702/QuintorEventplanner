package nl.han.oose.clipper.clipperapi.domain.event.data;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value="SELECT email FROM users u INNER JOIN events e ON u.user_id = e.user_id WHERE e.event_id = ?1", nativeQuery = true)
    String getOwnersEmailByEventId(Long id);

    @Query(value="SELECT e.event_id, r.registration_id, r.user_id, e.title, e.description, e.briefDescription, e.startDateTime, e.location, e.isApproved, e.isGuestEnabled, e.isGraduateChecked FROM events e INNER JOIN registrations r ON e.event_id = r.event_id WHERE r.user_id = ?1", nativeQuery = true)
    List<Event> getEventsByRegistrationAndUser(Long userId);

    @Query(value="SELECT email FROM users u WHERE u.user_id NOT IN (SELECT r.user_id FROM registrations r WHERE r.event_id = ?1)", nativeQuery = true)
    List<String> getUsersEmailNotEnrolled(Long id);
}
