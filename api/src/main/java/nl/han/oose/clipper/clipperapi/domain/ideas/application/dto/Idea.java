package nl.han.oose.clipper.clipperapi.domain.ideas.application.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "ideas")
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ideas_id;
    private Long user_id;
    private String idea;
    private Long likes;

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

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}
