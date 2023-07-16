package nl.han.oose.clipper.clipperapi.domain.ideas.application.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "user_likes_ideas")
public class UserLikesIdea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ideas_id;
    private Long user_id;
    private boolean liked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdeas_id() {
        return ideas_id;
    }

    public void setIdeas_id(Long ideas_id) {
        this.ideas_id = ideas_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
