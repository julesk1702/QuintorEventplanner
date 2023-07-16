package nl.han.oose.clipper.clipperapi.domain.ideas.data;

import nl.han.oose.clipper.clipperapi.domain.ideas.application.dto.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdeasRepository extends JpaRepository<Idea, Long> {
    @Modifying
    @Query("UPDATE Idea SET likes = likes + 1 WHERE ideas_id = ?1")
    void likeIdea(Long id, Long userId);
    @Modifying
    @Query("UPDATE Idea SET likes = likes - 1 WHERE ideas_id = ?1")
    void dislikeIdea(Long id, Long userId);
    @Modifying
    @Query("SELECT i.ideas_id, uli.user_id, uli.liked FROM Idea i INNER JOIN UserLikesIdea uli ON i.ideas_id = uli.ideas_id WHERE i.ideas_id = ?1 AND uli.user_id = ?2")
    List<String> checkIfUserHasLiked(Long id, Long userId);
}