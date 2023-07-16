package nl.han.oose.clipper.clipperapi.domain.event.data;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Modifying
    @Query("DELETE FROM Guest g WHERE g.event_id = ?1 AND g.user_id = ?2")
    void unRegisterGuests(Long eventId, Long userId);

    @Query(value = "SELECT g.* FROM guests g WHERE g.event_id = :eventId", nativeQuery = true)
    List<Guest> getGuestsByEventId(Long eventId);

}
