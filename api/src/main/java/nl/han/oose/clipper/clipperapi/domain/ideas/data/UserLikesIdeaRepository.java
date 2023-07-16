package nl.han.oose.clipper.clipperapi.domain.ideas.data;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.UserLikesIdea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserLikesIdeaRepository extends JpaRepository<UserLikesIdea, Long> {
    @Modifying
    @Query("UPDATE UserLikesIdea uli " +
            "SET uli.liked = true " +
            "WHERE uli.ideas_id = ?1 AND uli.user_id = ?2")
    void saveLike(Long id, Long userId);

    @Modifying
    @Query("UPDATE UserLikesIdea uli " +
            "SET uli.liked = false " +
            "WHERE uli.ideas_id = ?1 AND uli.user_id = ?2")
    void saveDislike(Long id, Long userId);

}