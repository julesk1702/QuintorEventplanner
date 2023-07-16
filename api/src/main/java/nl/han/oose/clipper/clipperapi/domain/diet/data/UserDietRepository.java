package nl.han.oose.clipper.clipperapi.domain.diet.data;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserDiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserDietRepository extends JpaRepository<UserDiet, Long> {
    @Query("SELECT ud.diet FROM UserDiet ud JOIN ud.diet d WHERE ud.user.user_id = :user_id")
    Optional<List<Diet>> findDietsByUserId(Long user_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserDiet ud WHERE ud.user.user_id = :user_id")
    void deleteByUserId(Long user_id);
}
