package nl.han.oose.clipper.clipperapi.domain.diet.data;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DietRepository extends JpaRepository<Diet, Long> {

    @Query(value = "SELECT name, SUM(count) AS total_count " +
            "FROM ( " +
            "  SELECT d.name, COUNT(d.diet_id) AS count " +
            "  FROM registrations r " +
            "  JOIN user_diets ud ON r.user_id = ud.user_id " +
            "  JOIN diets d ON ud.diet_id = d.diet_id " +
            "  WHERE r.event_id = :eventId " +
            "  GROUP BY d.diet_id, d.name " +
            "  UNION ALL " +
            "  SELECT d.name, COUNT(d.diet_id) AS count " +
            "  FROM guests g " +
            "  JOIN guest_diets gd ON g.guest_id = gd.guest_id " +
            "  JOIN diets d ON gd.diet_id = d.diet_id " +
            "  WHERE g.event_id = :eventId " +
            "  GROUP BY d.diet_id, d.name " +
            ") AS combined " +
            "GROUP BY name", nativeQuery = true)
    List<Object[]> findDietCountsByEventId(@Param("eventId") Long eventId);

}
