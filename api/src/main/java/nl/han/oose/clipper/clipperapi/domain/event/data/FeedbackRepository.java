package nl.han.oose.clipper.clipperapi.domain.event.data;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value="SELECT * FROM event_feedback WHERE event_id = ?1 AND user_id = ?2", nativeQuery = true)
    Feedback findByEventIdAndUserId(Long event_id, Long user_id);

    @Query(value="SELECT * FROM event_feedback WHERE event_id = ?1", nativeQuery = true)
    List<Feedback> findAllByEventId(Long event_id);

}
